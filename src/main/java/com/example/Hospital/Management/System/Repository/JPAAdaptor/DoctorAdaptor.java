package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.DoctorMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBDoctorRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort; // IMPORT NOU
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoctorAdaptor implements AbstractRepository<Doctor> {

    private final DBDoctorRepository jpaRepository;
    private final AbstractRepository<Department> departmentRepository;

    public DoctorAdaptor(
            DBDoctorRepository jpaRepository,
            @Qualifier("departmentAdaptor") AbstractRepository<Department> departmentRepository
    ) {
        this.jpaRepository = jpaRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void save(Doctor domain) {
        if (domain == null) {
            throw new IllegalArgumentException("Doctorul nu poate fi null.");
        }

        if (domain.getMedicalSpeciality() == null) {
            throw new RuntimeException("Specializarea medicala este obligatorie.");
        }

        validateDepartmentExists(domain.getDepartmentID());

        if (domain.getStaffID() == null || !isExistingDoctorInSpeciality(domain)) {
            if (jpaRepository.existsByStaffEmailAndMedicalSpeciality(
                    domain.getStaffEmail(),
                    domain.getMedicalSpeciality()
            )) {
                throw new RuntimeException("Doctorul cu email-ul '" + domain.getStaffEmail() +
                        "' este deja inregistrat pentru specializarea: " + domain.getMedicalSpeciality() +
                        ". Puteti adauga acelasi doctor, dar doar cu o alta specializare.");
            }
        }

        jpaRepository.save(DoctorMapper.toEntity(domain));
    }

    private void validateDepartmentExists(String departmentId) {
        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId);

            if (department == null) {
                throw new RuntimeException("Departamentul cu ID-ul '" + departmentId +
                        "' nu exista. Va rugati sa introduceti un ID de departament valid.");
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
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void delete(Doctor domain) {
        if (domain == null) {
            throw new IllegalArgumentException("Doctorul nu poate fi null.");
        }
        if (domain.getStaffID() == null || domain.getStaffID().trim().isEmpty()) {
            throw new IllegalArgumentException("ID-ul doctorului trebuie specificat pentru stergere.");
        }

        Long id = MapperUtils.parseLong(domain.getStaffID());
        if (id == null) {
            throw new IllegalArgumentException("ID-ul doctorului este invalid.");
        }

        jpaRepository.deleteById(id);
    }

    @Override
    public Doctor findById(String id) {
        Long parsed = MapperUtils.parseLong(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed).map(DoctorMapper::toDomain).orElse(null);
    }

    @Override
    public List<Doctor> findAll() {
        return jpaRepository.findAll().stream().map(DoctorMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Doctor> findAll(Sort sort) {
        if (sort == null) {
            return findAll();
        }
        return jpaRepository.findAll(sort).stream()
                .map(DoctorMapper::toDomain)
                .collect(Collectors.toList());
    }
}