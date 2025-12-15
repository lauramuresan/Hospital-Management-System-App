package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.DoctorMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import com.example.Hospital.Management.System.Model.GeneralModel.Department;

import com.example.Hospital.Management.System.Model.DBModel.DoctorEntity;
import com.example.Hospital.Management.System.SearchCriteria.DoctorSearchCriteria;
import com.example.Hospital.Management.System.Repository.JPA.DoctorSpecification;
import org.springframework.data.jpa.domain.Specification;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBDoctorRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
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

        validateDepartmentExists(domain.getDepartmentID());

        if (domain.getStaffID() == null || !isExistingDoctorInSpeciality(domain)) {
            if (jpaRepository.existsByStaffEmailAndMedicalSpeciality(domain.getStaffEmail(), domain.getMedicalSpeciality())) {
                throw new RuntimeException("Doctorul cu email-ul '" + domain.getStaffEmail() +
                        "' este deja inregistrat pentru specializarea: " + domain.getMedicalSpeciality());
            }
        }

        jpaRepository.save(mapper.toEntity(domain));
    }

    private void validateDepartmentExists(String departmentId) {
        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId);
            if (department == null) {
                throw new RuntimeException("Departamentul cu ID-ul '" + departmentId + "' nu exista.");
            }
        }
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
        if (domain != null && domain.getStaffID() != null) {
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
        // AICI ERA PROBLEMA DIN POZA 2: DoctorEntity nu era importat
        Specification<DoctorEntity> spec = null;

        if (searchCriteria instanceof DoctorSearchCriteria) {
            spec = DoctorSpecification.filterByCriteria((DoctorSearchCriteria) searchCriteria);
        }

        return jpaRepository.findAll(spec, sort).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}