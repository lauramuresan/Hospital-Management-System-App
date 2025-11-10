package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.Department;
import org.springframework.stereotype.Repository;
@Repository
public class DepartmentInMemoryRepository extends InMemoryRepository<Department> {
    @Override
    protected String getId(Department department) {
        return department.getDepartmentID();
    }
    @Override
    protected void setId(Department department, String id) {
        department.setDepartmentID(id);
    }
}
