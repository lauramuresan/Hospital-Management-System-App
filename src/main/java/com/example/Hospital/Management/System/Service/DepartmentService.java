package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importul necesar

import java.util.List;

@Service
@Transactional
public class DepartmentService extends BaseService<Department>{

    private final AbstractRepository<Department> departmentRepository;

    public DepartmentService(RepositoryFactory factory) {
        this.departmentRepository = factory.createRepository(Department.class);
    }

    @Override
    public void save(Department entity){
        departmentRepository.save(entity);
    }
    @Override
    protected void delete(Department entity){
        departmentRepository.delete(entity);
    }
    @Override
    public Department findById(String id){
        return departmentRepository.findById(id);
    }
    @Override
    protected List<Department> findAll(){
        return departmentRepository.findAll();
    }
}