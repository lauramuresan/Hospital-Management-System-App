package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.Department;
import com.example.Hospital.Management.System.Repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    public void addDepartment(Department department) {
        departmentRepository.save(department);
    }
    public void removeDepartment(String departmentId) {
        Department department = departmentRepository.findById(departmentId);
        if (department != null) {
            departmentRepository.delete(department);
        }
    }
    public Department getDepartmentById(String id) {
        return departmentRepository.findById(id);
    }
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
