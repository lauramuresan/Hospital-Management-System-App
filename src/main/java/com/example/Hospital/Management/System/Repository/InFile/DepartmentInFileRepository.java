package com.example.Hospital.Management.System.Repository.InFile;

import com.example.Hospital.Management.System.Model.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository("departmentInFile")
public class DepartmentInFileRepository extends InFileRepository<Department> {

    public DepartmentInFileRepository(ObjectMapper mapper) {
        super(mapper, "./data", "departments.json");
    }

    @Override
    protected String getId(Department department) {
        return department.getDepartmentID();
    }

    @Override
    protected void setId(Department department, String id) {
        department.setDepartmentID(id);
    }
}
