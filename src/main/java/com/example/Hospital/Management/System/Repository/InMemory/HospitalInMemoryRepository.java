package com.example.Hospital.Management.System.Repository.InMemory;
import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import org.springframework.stereotype.Repository;
@Repository("hospitalInMemory")
public class HospitalInMemoryRepository extends InMemoryRepository<Hospital> {
    @Override
    protected String getId(Hospital hospital) {
        return hospital.getHospitalID();
    }
    @Override
    protected void setId(Hospital hospital, String id) {
        hospital.setHospitalID(id);
    }
}
