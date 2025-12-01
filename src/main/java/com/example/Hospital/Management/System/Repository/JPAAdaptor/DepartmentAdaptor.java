package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.DepartmentMapper;
import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity; // ✅ EROARE CORECTATĂ: Import adăugat
import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBDepartmentRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBHospitalRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentAdaptor implements AbstractRepository<Department> {

    private final DBDepartmentRepository jpaRepository;
    private final DBHospitalRepository hospitalJpaRepository;

    public DepartmentAdaptor(DBDepartmentRepository jpaRepository, DBHospitalRepository hospitalJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.hospitalJpaRepository = hospitalJpaRepository;
    }

    @Override
    public void save(Department domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Department");

        // 1. Validare FK (Hospital ID)
        Long hospitalId = RepositoryValidationUtils.parseIdOrThrow(
                domain.getHospitalID(),
                "ID-ul Spitalului este obligatoriu și trebuie să fie un număr valid."
        );

        if (!hospitalJpaRepository.existsById(hospitalId)) {
            throw new RuntimeException("Spitalul cu ID-ul " + hospitalId + " nu a fost găsit.");
        }

        // 2. Validare Unicitate (Nume + Hospital ID)
        if (domain.getDepartmentName() == null || domain.getDepartmentName().isBlank()) {
            throw new IllegalArgumentException("Numele departamentului este obligatoriu.");
        }

        if (domain.getDepartmentID() == null || !isExistingDepartmentNameInHospital(domain)) {
            if (jpaRepository.existsByDepartmentNameAndHospitalId(domain.getDepartmentName(), hospitalId)) {
                throw new RuntimeException("Departamentul '" + domain.getDepartmentName() +
                        "' există deja în Spitalul cu ID-ul " + hospitalId + ".");
            }
        }

        jpaRepository.save(DepartmentMapper.toEntity(domain));
    }

    private boolean isExistingDepartmentNameInHospital(Department domain) {
        if (domain.getDepartmentID() == null) return false;
        try {
            Long id = RepositoryValidationUtils.parseIdOrNull(domain.getDepartmentID());

            if (id == null) {
                throw new NumberFormatException("ID-ul departamentului nu este valid.");
            }

            return jpaRepository.findById(id)
                    .map(DepartmentEntity::getDepartmentName)
                    .filter(name -> name.equals(domain.getDepartmentName()))
                    .isPresent();
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public void delete(Department domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Department");
        RepositoryValidationUtils.requireIdForDelete(domain.getDepartmentID(), "Department");

        Long id = RepositoryValidationUtils.parseIdOrThrow(domain.getDepartmentID(), "Invalid Department ID for delete");
        jpaRepository.deleteById(id);
    }

    @Override
    public Department findById(String id) {
        Long parsed = RepositoryValidationUtils.parseIdOrNull(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed).map(DepartmentMapper::toDomain).orElse(null);
    }

    @Override
    public List<Department> findAll() {
        return jpaRepository.findAll().stream().map(DepartmentMapper::toDomain).collect(Collectors.toList());
    }
}