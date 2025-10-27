package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.Patient;
import com.example.Hospital.Management.System.Repository.PatientRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void addPatient(Patient patient) {
        patientRepository.save(patient);
    }

    public void removePatient(String patientId) {
        Patient patient = patientRepository.findById(patientId);
        if (patient != null) patientRepository.delete(patient);
    }

    public Patient getPatientById(String id) {
        return patientRepository.findById(id);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

}
