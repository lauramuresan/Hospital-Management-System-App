package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.Appointment;
import com.example.Hospital.Management.System.Model.Department;
import com.example.Hospital.Management.System.Repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService extends BaseService<Department>{

    private final DepartmentRepository departmentRepository;
    public DepartmentService(DepartmentRepository departmentRepository) {
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
