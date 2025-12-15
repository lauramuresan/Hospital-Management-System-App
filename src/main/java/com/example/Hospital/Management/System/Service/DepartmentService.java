package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import com.example.Hospital.Management.System.Repository.RepositoryMode;
import com.example.Hospital.Management.System.Repository.RepositoryModeHolder;
import com.example.Hospital.Management.System.Utils.ReflectionSorter;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentService extends BaseService<Department>{

    private final AbstractRepository<Department> departmentRepository;
    private final RepositoryModeHolder modeHolder;

    public DepartmentService(RepositoryFactory factory, RepositoryModeHolder modeHolder) {
        this.departmentRepository = factory.createRepository(Department.class);
        this.modeHolder = modeHolder;
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

    @Override
    protected List<Department> findAll(Sort sort) {
        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return departmentRepository.findAll(sort);
        } else {
            List<Department> departments = departmentRepository.findAll();

            if (sort != null && sort.isSorted()) {
                ReflectionSorter.sortList(departments, Department.class, sort);
            }
            return departments;
        }
    }
}