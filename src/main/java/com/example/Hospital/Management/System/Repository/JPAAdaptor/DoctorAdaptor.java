package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.DoctorMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Model.DBModel.DoctorEntity;
import com.example.Hospital.Management.System.SearchCriteria.DoctorSearchCriteria;
import com.example.Hospital.Management.System.Repository.JPA.DoctorSpecification;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBDoctorRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoctorAdaptor implements AbstractRepository<Doctor> {

    private final DBDoctorRepository jpaRepository;
    private final AbstractRepository<Department> departmentRepository;
    private final DoctorMapper mapper;

    public DoctorAdaptor(
            DBDoctorRepository jpaRepository,
            @Qualifier("departmentAdaptor") AbstractRepository<Department> departmentRepository,
            DoctorMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Doctor domain) {
        if (domain == null) throw new IllegalArgumentException("Doctorul nu poate fi null.");
        if (domain.getMedicalSpeciality() == null) throw new RuntimeException("Specializarea medicala este obligatorie.");
        if (domain.getLicenseNumber() == null || domain.getLicenseNumber().isEmpty()) throw new RuntimeException("Numărul de licență este obligatoriu.");

        validateDepartmentExists(domain.getDepartmentID());

        boolean isNewEntry = domain.getStaffID() == null || domain.getStaffID().isEmpty();

        // 1. VERIFICARE UNICITATE EMAIL
        if (isNewEntry || !isEmailUnchanged(domain)) {
            if (jpaRepository.existsByStaffEmail(domain.getStaffEmail())) {
                throw new RuntimeException("Adresa de email '" + domain.getStaffEmail() +
                        "' este deja utilizată de un alt membru al personalului medical. Vă rugăm folosiți un email unic.");
            }
        }

        // 2. VERIFICARE UNICITATE NUMĂR DE LICENȚĂ
        if (isNewEntry || !isLicenseNumberUnchanged(domain)) {
            if (jpaRepository.existsByLicenseNumber(domain.getLicenseNumber())) {
                throw new RuntimeException("Numărul de licență '" + domain.getLicenseNumber() +
                        "' este deja înregistrat. Vă rugăm verificați datele.");
            }
        }

        jpaRepository.save(mapper.toEntity(domain));
    }

    private void validateDepartmentExists(String departmentId) {
        if (departmentId != null && !departmentId.isEmpty()) {
            Department department = departmentRepository.findById(departmentId);
            if (department == null) {
                throw new RuntimeException("Departamentul cu ID-ul '" + departmentId + "' nu există.");
            }
        }
    }

    private boolean isEmailUnchanged(Doctor domain) {
        if (domain.getStaffID() == null || domain.getStaffID().isEmpty()) return false;
        try {
            Long id = MapperUtils.parseLong(domain.getStaffID());
            return jpaRepository.findById(id)
                    .map(DoctorEntity::getStaffEmail)
                    .filter(existingEmail -> existingEmail.equals(domain.getStaffEmail()))
                    .isPresent();
        } catch (NumberFormatException e) { return false; }
    }

    private boolean isLicenseNumberUnchanged(Doctor domain) {
        if (domain.getStaffID() == null || domain.getStaffID().isEmpty()) return false;
        try {
            Long id = MapperUtils.parseLong(domain.getStaffID());
            return jpaRepository.findById(id)
                    .map(DoctorEntity::getLicenseNumber)
                    .filter(existingLicense -> existingLicense.equals(domain.getLicenseNumber()))
                    .isPresent();
        } catch (NumberFormatException e) { return false; }
    }

    private boolean isExistingDoctorInSpeciality(Doctor domain) {
        if (domain.getStaffID() == null) return false;
        try {
            Long id = MapperUtils.parseLong(domain.getStaffID());
            return jpaRepository.findById(id)
                    .filter(entity -> entity.getStaffEmail().equals(domain.getStaffEmail()) &&
                            entity.getMedicalSpeciality().equals(domain.getMedicalSpeciality()))
                    .isPresent();
        } catch (NumberFormatException e) { return false; }
    }

    @Override
    public void delete(Doctor domain) {
        if (domain != null && domain.getStaffID() != null && !domain.getStaffID().isEmpty()) {
            jpaRepository.deleteById(MapperUtils.parseLong(domain.getStaffID()));
        }
    }

    @Override
    public Doctor findById(String id) {
        Long parsed = MapperUtils.parseLong(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed).map(mapper::toDomain).orElse(null);
    }

    @Override
    public List<Doctor> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Doctor> findAll(Sort sort) {
        return findAll(null, sort);
    }

    @Override
    public List<Doctor> findAll(Object searchCriteria, Sort sort) {
        Specification<DoctorEntity> spec = null;

        if (searchCriteria instanceof DoctorSearchCriteria) {
            spec = DoctorSpecification.filterByCriteria((DoctorSearchCriteria) searchCriteria);
        }

        return jpaRepository.findAll(spec, sort).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}