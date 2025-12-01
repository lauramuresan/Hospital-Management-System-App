package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Nurse;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class NurseService extends BaseService<Nurse> {
    private final AbstractRepository<Nurse> nurseRepository;

    public NurseService(RepositoryFactory factory) {
        this.nurseRepository = factory.createRepository(Nurse.class);
    }

    @Override
    public void save(Nurse entity){
        nurseRepository.save(entity);
    }
    @Override
    protected void delete(Nurse entity){ // Adăugat @Override
        nurseRepository.delete(entity);
    }
    @Override
    protected Nurse findById(String id){ // Adăugat @Override
        return nurseRepository.findById(id);
    }
    @Override
    protected List<Nurse> findAll(){ // Adăugat @Override
        return nurseRepository.findAll();
    }
}