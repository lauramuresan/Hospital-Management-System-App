package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.DepartmentMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBDepartmentRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBHospitalRepository;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Sort;

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
        if (domain == null) {
            throw new IllegalArgumentException("Department nu poate fi null.");
        }


        Long hospitalId = MapperUtils.parseLong(domain.getHospitalID());
        if (hospitalId == null) {
            throw new IllegalArgumentException("ID-ul Spitalului este obligatoriu si trebuie sa fie un numar valid.");
        }

        if (!hospitalJpaRepository.existsById(hospitalId)) {
            throw new RuntimeException("Spitalul cu ID-ul " + hospitalId + " nu a fost gasit.");
        }

        // 2. Validare Unicitate (Nume + Hospital ID)
        if (domain.getDepartmentName() == null || domain.getDepartmentName().isBlank()) {
            throw new IllegalArgumentException("Numele departamentului este obligatoriu.");
        }

        if (domain.getDepartmentID() == null || !isExistingDepartmentNameInHospital(domain)) {
            if (jpaRepository.existsByDepartmentNameAndHospitalId(domain.getDepartmentName(), hospitalId)) {
                throw new RuntimeException("Departamentul '" + domain.getDepartmentName() +
                        "' exista deja in Spitalul cu ID-ul " + hospitalId + ".");
            }
        }

        jpaRepository.save(DepartmentMapper.toEntity(domain));
    }

    private boolean isExistingDepartmentNameInHospital(Department domain) {
        if (domain.getDepartmentID() == null) return false;
        try {
            Long id = MapperUtils.parseLong(domain.getDepartmentID());

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
        if (domain == null) {
            throw new IllegalArgumentException("Department nu poate fi null.");
        }
        if (domain.getDepartmentID() == null || domain.getDepartmentID().trim().isEmpty()) {
            throw new IllegalArgumentException("ID-ul Department trebuie specificat pentru stergere.");
        }

        Long id = MapperUtils.parseLong(domain.getDepartmentID());
        if (id == null) {
            throw new IllegalArgumentException("Invalid Department ID for delete");
        }

        jpaRepository.deleteById(id);
    }

    @Override
    public Department findById(String id) {
        Long parsed = MapperUtils.parseLong(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed).map(DepartmentMapper::toDomain).orElse(null);
    }

    @Override
    public List<Department> findAll() {
        return jpaRepository.findAll().stream().map(DepartmentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Department> findAll(Sort sort) {
        if (sort == null) {
            return findAll();
        }
        return jpaRepository.findAll(sort).stream()
                .map(DepartmentMapper::toDomain)
                .collect(Collectors.toList());
    }
}