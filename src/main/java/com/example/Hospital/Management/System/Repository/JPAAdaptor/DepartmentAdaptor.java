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
    private final DBHospitalRepository hospitalJpaRepository;

    public DepartmentAdaptor(DBDepartmentRepository jpaRepository, DBHospitalRepository hospitalJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.hospitalJpaRepository = hospitalJpaRepository;
    }

    @Override
    public void save(Department domain) {
        // Business Validation: Verifică existența Spitalului părinte (FK)
        if (domain.getHospitalID() == null || !hospitalJpaRepository.existsById(Long.valueOf(domain.getHospitalID()))) {
            throw new RuntimeException("Spitalul specificat cu ID-ul " + domain.getHospitalID() + " nu există.");
        }

        // CORECTAT: Apelăm metoda statică direct pe clasa DepartmentMapper
        jpaRepository.save(DepartmentMapper.toEntity(domain));
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
            return jpaRepository.findById(Long.valueOf(id))
                    .map(DepartmentMapper::toDomain)
                    .orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Department> findAll() {
        return jpaRepository.findAll().stream()
                .map(DepartmentMapper::toDomain)
                .collect(Collectors.toList());
    }
}