package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Adăugat
import java.util.List;

@Service
@Transactional
public class DoctorService extends BaseService<Doctor>{
    private final AbstractRepository<Doctor> doctorRepository;

    public DoctorService(RepositoryFactory factory) {
        this.doctorRepository = factory.createRepository(Doctor.class);
    }

    @Override
    public void save(Doctor entity){
        doctorRepository.save(entity);
    }
    @Override
    protected void delete(Doctor entity){ // Adăugat @Override
        doctorRepository.delete(entity);
    }
    @Override
    public Doctor findById(String id){ // Adăugat @Override
        return doctorRepository.findById(id);
    }
    @Override
    public List<Doctor> findAll(){ // Adăugat @Override
        return doctorRepository.findAll();
    }
}