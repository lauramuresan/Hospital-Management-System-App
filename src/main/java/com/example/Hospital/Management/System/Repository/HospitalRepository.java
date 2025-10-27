package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.Hospital;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class HospitalRepository implements AbstractRepository<Hospital> {

    private final Map<String, Hospital> hospitals = new HashMap<>();

    @Override
    public void save(Hospital hospital) {
        if (hospital.getHospitalID() == null || hospital.getHospitalID().isEmpty()) {
            hospital.setHospitalID(UUID.randomUUID().toString());
        }
        hospitals.put(hospital.getHospitalID(), hospital);
    }

    @Override
    public void delete(Hospital hospital) {
        hospitals.remove(hospital.getHospitalID());
    }

    @Override
    public List<Hospital> findAll() {
        return new ArrayList<>(hospitals.values());
    }

    @Override
    public Hospital findById(String id) {
        return hospitals.get(id);
    }

}