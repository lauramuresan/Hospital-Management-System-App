package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.Department;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.InMemory.DepartmentInMemoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService extends BaseService<Department>{

    private final AbstractRepository<Department> departmentRepository;

    public DepartmentService(AbstractRepository<Department> departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    protected void save(Department entity){
        departmentRepository.save(entity);
    }
    protected void delete(Department entity){
        departmentRepository.delete(entity);
    }
    protected Department findById(String id){
        return departmentRepository.findById(id);
    }
    protected List<Department> findAll(){
        return departmentRepository.findAll();
    }
    }
