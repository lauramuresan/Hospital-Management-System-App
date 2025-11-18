package com.example.Hospital.Management.System.Repository.InFile;

import com.example.Hospital.Management.System.Model.Doctor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository("doctorInFile")
public class DoctorInFileRepository extends InFileRepository<Doctor> {

    public DoctorInFileRepository(ObjectMapper mapper) {
        super(mapper, "./data", "doctors.json");
    }

    @Override
    protected String getId(Doctor doctor) {
        return doctor.getStaffID();
    }

    @Override
    protected void setId(Doctor doctor, String id) {
        doctor.setStaffID(id);
    }
}
