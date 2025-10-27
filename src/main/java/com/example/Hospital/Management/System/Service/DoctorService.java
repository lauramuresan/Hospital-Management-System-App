package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.Doctor;
import com.example.Hospital.Management.System.Repository.DoctorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    public void addDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }
    public void removeDoctor(String doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId);
        if (doctor != null) doctorRepository.delete(doctor);
    }
    public Doctor getDoctorById(String id) {
        return doctorRepository.findById(id);
    }
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}