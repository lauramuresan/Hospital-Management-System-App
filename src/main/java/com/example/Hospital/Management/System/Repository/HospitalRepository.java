package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.Hospital;
import org.springframework.stereotype.Repository;
@Repository
public class HospitalRepository extends InMemoryRepository<Hospital> {
    @Override
    protected String getId(Hospital hospital) {
        return hospital.getHospitalID();
    }
    @Override
    protected void setId(Hospital hospital, String id) {
        hospital.setHospitalID(id);
    }
}
