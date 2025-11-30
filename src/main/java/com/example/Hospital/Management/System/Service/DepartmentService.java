package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importul necesar

import java.util.List;

@Service
@Transactional // <<< Adăugați @Transactional aici!
public class DepartmentService extends BaseService<Department>{

    private final AbstractRepository<Department> departmentRepository;

    public DepartmentService(RepositoryFactory factory) {
        this.departmentRepository = factory.createRepository(Department.class);
    }

    @Override
    protected void save(Department entity){
        departmentRepository.save(entity);
    }

    @Override // Adăugați @Override pentru consistență
    protected void delete(Department entity){
        departmentRepository.delete(entity);
    }

    @Override // Adăugați @Override pentru consistență
    protected Department findById(String id){
        return departmentRepository.findById(id);
    }

    @Override // Adăugați @Override pentru consistență
    protected List<Department> findAll(){
        return departmentRepository.findAll();
    }
}