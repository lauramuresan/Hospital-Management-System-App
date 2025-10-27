package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.Hospital;
import com.example.Hospital.Management.System.Repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }
    public void addHospital(Hospital hospital) {
        hospitalRepository.save(hospital);
    }
    public void removeHospital(String hospitalId) {
        Hospital hospital = hospitalRepository.findById(hospitalId);
        if (hospital != null) {
            hospitalRepository.delete(hospital);
        }
    }
    public Hospital getHospitalById(String id) {
        return hospitalRepository.findById(id);
    }
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

}
