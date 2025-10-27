package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.Patient;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class PatientRepository implements AbstractRepository<Patient> {
    private final HashMap<String, Patient> patients = new HashMap<>();
    @Override
    public void save(Patient patient) {
        if(patient.getPatientID() == null || patient.getPatientID().isEmpty()){
            patient.setPatientID(UUID.randomUUID().toString());
        }
        patients.put(patient.getPatientID(), patient);
    }
    @Override
    public Patient findById(String id) {
        return patients.get(id);
    }
    @Override
    public void delete(Patient patient) {
        patients.remove(patient.getPatientID());
    }
    @Override
    public List<Patient> findAll() {
        return new ArrayList<>(patients.values());
    }
}
