package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.Department;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DepartmentRepository implements AbstractRepository<Department> {
    private final HashMap<String, Department> departments = new HashMap<>();

    @Override
    public void save(Department department) {
        if(department==null || department.getDepartmentID().isEmpty()){
            department.setDepartmentID(UUID.randomUUID().toString());
        }
        departments.put(department.getDepartmentID(), department);
    }
    @Override
    public void delete(Department department) {
        departments.remove(department.getDepartmentID());
    }
    @Override
    public Department findById(String id) {
        return departments.get(id);
    }
    @Override
    public List<Department> findAll(){
        return new ArrayList<>(departments.values());
    }
}
