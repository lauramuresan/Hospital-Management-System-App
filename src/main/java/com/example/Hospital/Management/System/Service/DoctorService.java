package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.Department;
import com.example.Hospital.Management.System.Model.Doctor;
import com.example.Hospital.Management.System.Repository.DoctorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class DoctorService extends BaseService<Doctor>{
    private final DoctorRepository doctorRepository;
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
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