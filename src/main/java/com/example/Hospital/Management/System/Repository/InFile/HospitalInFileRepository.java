package com.example.Hospital.Management.System.Repository.InFile;

import com.example.Hospital.Management.System.Model.Hospital;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository("hospitalInFile")
public class HospitalInFileRepository extends InFileRepository<Hospital> {

    public HospitalInFileRepository(ObjectMapper mapper) {
        super(mapper, "./data", "hospitals.json");
    }

    @Override
    protected String getId(Hospital hospital) {
        return hospital.getHospitalID();
    }

    @Override
    protected void setId(Hospital hospital, String id) {
        hospital.setHospitalID(id);
    }
}
