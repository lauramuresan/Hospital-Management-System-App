package com.example.Hospital.Management.System.Repository.InFile;

import com.example.Hospital.Management.System.Model.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository("patientInFile")
public class PatientInFileRepository extends InFileRepository<Patient> {

    // Forțăm calea să fie EXACT: "./data/patients.json"
    public PatientInFileRepository(ObjectMapper mapper) {
        super(mapper, "./data", "patients.json");
    }

    @Override
    protected String getId(Patient patient) {
        return patient.getPatientID();
    }

    @Override
    protected void setId(Patient patient, String id) {
        patient.setPatientID(id);
    }
}
