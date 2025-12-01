package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Adăugat
import java.util.List;

@Service
@Transactional // Adăugat
public class PatientService extends BaseService<Patient>{

    private final AbstractRepository<Patient> patientRepository;

    public PatientService(RepositoryFactory factory) {
        this.patientRepository = factory.createRepository(Patient.class);
    }

    @Override
    public void save(Patient entity){
        patientRepository.save(entity);
    }
    @Override
    protected void delete(Patient entity){ // Adăugat @Override
        patientRepository.delete(entity);
    }
    @Override
    public Patient findById(String id){ // Vizibilitate uniformizată și @Override
        return patientRepository.findById(id);
    }
    @Override
    public List<Patient> findAll(){ // Vizibilitate uniformizată și @Override
        return patientRepository.findAll();
    }
}