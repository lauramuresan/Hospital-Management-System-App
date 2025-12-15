package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.NurseMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.NurseEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Nurse;
import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.SearchCriteria.NurseSearchCriteria; // IMPORT
import com.example.Hospital.Management.System.Repository.JPA.NurseSpecification; // IMPORT
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBNurseRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NurseAdaptor implements AbstractRepository<Nurse> {

    private final DBNurseRepository jpaRepository;
    private final AbstractRepository<Department> departmentRepository;
    private final NurseMapper mapper; // Injectat

    public NurseAdaptor(
            DBNurseRepository jpaRepository,
            @Qualifier("departmentAdaptor") AbstractRepository<Department> departmentRepository,
            NurseMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Nurse domain) {
        if (domain == null) throw new IllegalArgumentException("Asistentul Medical nu poate fi null.");
        if (domain.getQualificationLevel() == null) throw new RuntimeException("Categoria este obligatorie.");

        validateDepartmentExists(domain.getDepartmentID());

        if (domain.getStaffID() == null || !isExistingEmail(domain)) {
            if (jpaRepository.existsByStaffEmail(domain.getStaffEmail())) {
                throw new RuntimeException("Adresa de email '" + domain.getStaffEmail() + "' este deja utilizata.");
            }
        }
        jpaRepository.save(mapper.toEntity(domain)); // Folosim instanța mapper
    }

    private void validateDepartmentExists(String departmentId) {
        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId);
            if (department == null) {
                throw new RuntimeException("Departamentul cu ID-ul '" + departmentId + "' nu exista.");
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
        } catch (NumberFormatException e) { return false; }
    }

    @Override
    public void delete(Nurse domain) {
        if (domain != null && domain.getStaffID() != null) {
            jpaRepository.deleteById(MapperUtils.parseLong(domain.getStaffID()));
        }
    }

    @Override
    public Nurse findById(String id) {
        Long parsed = MapperUtils.parseLong(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed).map(mapper::toDomain).orElse(null);
    }

    @Override
    public List<Nurse> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Nurse> findAll(Sort sort) {
        return findAll(null, sort);
    }

    @Override
    public List<Nurse> findAll(Object searchCriteria, Sort sort) {
        Specification<NurseEntity> spec = null;

        if (searchCriteria instanceof NurseSearchCriteria) {
            spec = NurseSpecification.filterByCriteria((NurseSearchCriteria) searchCriteria);
        }

        return jpaRepository.findAll(spec, sort).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}