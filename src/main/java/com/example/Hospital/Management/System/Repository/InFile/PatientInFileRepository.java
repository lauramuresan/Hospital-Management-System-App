package com.example.Hospital.Management.System.Repository.InFile;

import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository("patientInFile")
public class PatientInFileRepository extends InFileRepository<Patient> {

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
