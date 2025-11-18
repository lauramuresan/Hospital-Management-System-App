package com.example.Hospital.Management.System.Repository.InFile;

import com.example.Hospital.Management.System.Model.Nurse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository("nurseInFile")
public class NurseInFileRepository extends InFileRepository<Nurse> {

    public NurseInFileRepository(ObjectMapper mapper) {
        super(mapper, "./data", "nurses.json");
    }

    @Override
    protected String getId(Nurse nurse) {
        return nurse.getStaffID();
    }

    @Override
    protected void setId(Nurse nurse, String id) {
        nurse.setStaffID(id);
    }
}
