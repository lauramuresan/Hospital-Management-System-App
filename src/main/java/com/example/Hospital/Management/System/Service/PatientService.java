package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.Patient;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.InMemory.PatientInMemoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientService extends BaseService<Patient>{

    private final AbstractRepository<Patient> patientRepository;

    public PatientService(AbstractRepository<Patient> patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    protected void save(Patient entity){
        patientRepository.save(entity);
    }
    protected void delete(Patient entity){
        patientRepository.delete(entity);
    }
    protected Patient findById(String id){
        return patientRepository.findById(id);
    }
    protected List<Patient> findAll(){
        return patientRepository.findAll();
    }

}
