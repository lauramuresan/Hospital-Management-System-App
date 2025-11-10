package com.example.Hospital.Management.System.Repository.InMemory;
import com.example.Hospital.Management.System.Model.Patient;
import org.springframework.stereotype.Repository;
@Repository("patientInMemory")
public class PatientInMemoryRepository extends InMemoryRepository<Patient> {
    @Override
    protected String getId(Patient patient) {
        return patient.getPatientID();
    }
    @Override
    protected void setId(Patient patient, String id) {
        patient.setPatientID(id);
    }
}
