package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.NurseMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.NurseEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Nurse;
import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBNurseRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort; // IMPORT NOU
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NurseAdaptor implements AbstractRepository<Nurse> {

    private final DBNurseRepository jpaRepository;
    private final AbstractRepository<Department> departmentRepository;

    public NurseAdaptor(
            DBNurseRepository jpaRepository,
            @Qualifier("departmentAdaptor") AbstractRepository<Department> departmentRepository
    ) {
        this.jpaRepository = jpaRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void save(Nurse domain) {
        if (domain == null) {
            throw new IllegalArgumentException("Asistentul Medical nu poate fi null.");
        }

        if (domain.getQualificationLevel() == null) {
            throw new RuntimeException("Categoria (Nivelul de Calificare) este obligatorie.");
        }

        // 1. VALIDARE FK: Verificarea existenței Departamentului
        validateDepartmentExists(domain.getDepartmentID());

        // 2. VALIDARE BUSINESS: Unicitatea Email-ului
        if (domain.getStaffID() == null || !isExistingEmail(domain)) {
            if (jpaRepository.existsByStaffEmail(domain.getStaffEmail())) {
                throw new RuntimeException("Adresa de email '" + domain.getStaffEmail() + "' este deja utilizata de un alt membru al personalului.");
            }
        }
        jpaRepository.save(NurseMapper.toEntity(domain));
    }

    private void validateDepartmentExists(String departmentId) {
        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId);

            if (department == null) {
                throw new RuntimeException("Departamentul cu ID-ul '" + departmentId +
                        "' nu exista. Va rugam sa introduceti un ID de departament valid pentru asistentul medical.");
            }
        }
    }

    private boolean isExistingEmail(Nurse domain) {
        if (domain.getStaffID() == null) return false;
        try {
            Long id = MapperUtils.parseLong(domain.getStaffID());
            return jpaRepository.findById(id)
                    .map(NurseEntity::getStaffEmail)
                    .filter(email -> email.equals(domain.getStaffEmail()))
                    .isPresent();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void delete(Nurse domain) {
        if (domain == null) {
            throw new IllegalArgumentException("Asistentul Medical nu poate fi null.");
        }
        if (domain.getStaffID() == null || domain.getStaffID().trim().isEmpty()) {
            throw new IllegalArgumentException("ID-ul asistentului trebuie specificat pentru stergere.");
        }

        Long id = MapperUtils.parseLong(domain.getStaffID());
        if (id == null) {
            throw new IllegalArgumentException("ID-ul asistentului este invalid.");
        }

        jpaRepository.deleteById(id);
    }

    @Override
    public Nurse findById(String id) {
        Long parsed = MapperUtils.parseLong(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed).map(NurseMapper::toDomain).orElse(null);
    }

    @Override
    public List<Nurse> findAll() {
        return jpaRepository.findAll().stream().map(NurseMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Nurse> findAll(Sort sort) {
        if (sort == null) {
            return findAll();
        }
        return jpaRepository.findAll(sort).stream()
                .map(NurseMapper::toDomain)
                .collect(Collectors.toList());
    }
}