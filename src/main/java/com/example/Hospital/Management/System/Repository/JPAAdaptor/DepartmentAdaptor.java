package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.DepartmentMapper;
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
    private final DBHospitalRepository hospitalJpaRepository; // Pentru Business Validation
    private final DepartmentMapper mapper;

    public DepartmentAdaptor(DBDepartmentRepository jpaRepository, DBHospitalRepository hospitalJpaRepository, DepartmentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.hospitalJpaRepository = hospitalJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Department domain) {
        // Business Validation: Verifică existența Spitalului părinte (FK)
        if (domain.getHospitalID() == null || !hospitalJpaRepository.existsById(Long.valueOf(domain.getHospitalID()))) {
            throw new RuntimeException("Spitalul specificat cu ID-ul " + domain.getHospitalID() + " nu există.");
        }

        jpaRepository.save(mapper.toEntity(domain));
    }

    @Override
    public void delete(Department domain) {
        if (domain.getDepartmentID() != null) {
            jpaRepository.deleteById(Long.valueOf(domain.getDepartmentID()));
        }
    }

    @Override
    public Department findById(String id) {
        try {
            return jpaRepository.findById(Long.valueOf(id)).map(mapper::toDomain).orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Department> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}