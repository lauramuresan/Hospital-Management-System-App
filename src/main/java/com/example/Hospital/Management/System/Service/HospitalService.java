package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.Hospital;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.InMemory.HospitalInMemoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService extends BaseService<Hospital>{

    private final AbstractRepository<Hospital> hospitalRepository;

    public HospitalService(AbstractRepository<Hospital> hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    protected void save(Hospital entity){
        hospitalRepository.save(entity);
    }
    protected void delete(Hospital entity){
        hospitalRepository.delete(entity);
    }
    protected Hospital findById(String id){
        return hospitalRepository.findById(id);
    }
    protected List<Hospital> findAll(){
        return hospitalRepository.findAll();
    }

}
