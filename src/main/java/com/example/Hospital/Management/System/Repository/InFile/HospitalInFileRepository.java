package com.example.Hospital.Management.System.Repository.InFile;

import com.example.Hospital.Management.System.Model.Hospital;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("hospitalInFile")
public class HospitalInFileRepository extends InFileRepository<Hospital> {
    public HospitalInFileRepository(ObjectMapper mapper, @Value("${app.data.folder:./data}") String dataFolder) {
        super(mapper, dataFolder, "hospitals.json");

    }
    @Override
    protected String getId(Hospital hospital) { return hospital.getHospitalID(); }
    @Override
    protected void setId(Hospital hospital, String id) { hospital.setHospitalID(id); }
}