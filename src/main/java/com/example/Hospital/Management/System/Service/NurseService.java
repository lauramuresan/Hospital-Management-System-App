package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.Nurse;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.InMemory.NurseInMemoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NurseService extends BaseService<Nurse> {
    private final AbstractRepository<Nurse> nurseRepository;

    public NurseService(AbstractRepository<Nurse> nurseRepository) {
        this.nurseRepository = nurseRepository;
    }

    @Override
    protected void save(Nurse entity){
        nurseRepository.save(entity);
    }
    protected void delete(Nurse entity){
        nurseRepository.delete(entity);
    }
    protected Nurse findById(String id){
        return nurseRepository.findById(id);
    }
    protected List<Nurse> findAll(){
        return nurseRepository.findAll();
    }

}
