package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importul cheie

import java.util.List;

@Service
@Transactional // ADNOTARE ESENȚIALĂ PENTRU PERSISTENȚA JPA
public class HospitalService extends BaseService<Hospital>{

    private final AbstractRepository<Hospital> hospitalRepository;

    public HospitalService(RepositoryFactory factory) {
        this.hospitalRepository = factory.createRepository(Hospital.class);
    }


    @Override
    public void save(Hospital entity){
        hospitalRepository.save(entity);
    }

    @Override
    protected void delete(Hospital entity){
        hospitalRepository.delete(entity);
    }

    @Override
    protected Hospital findById(String id){
        return hospitalRepository.findById(id);
    }

    @Override
    protected List<Hospital> findAll(){
        return hospitalRepository.findAll();
    }
}