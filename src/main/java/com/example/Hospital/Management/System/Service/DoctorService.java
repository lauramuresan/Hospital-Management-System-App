package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class DoctorService extends BaseService<Doctor>{
    private final AbstractRepository<Doctor> doctorRepository;

    public DoctorService(RepositoryFactory factory) {
        this.doctorRepository = factory.createRepository(Doctor.class);
    }

    @Override
    protected void save(Doctor entity){
        doctorRepository.save(entity);
    }
    protected void delete(Doctor entity){
        doctorRepository.delete(entity);
    }
    protected Doctor findById(String id){
        return doctorRepository.findById(id);
    }
    protected List<Doctor> findAll(){
        return doctorRepository.findAll();
    }

}