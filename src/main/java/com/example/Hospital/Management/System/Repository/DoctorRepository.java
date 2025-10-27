package com.example.Hospital.Management.System.Repository;

import com.example.Hospital.Management.System.Model.Doctor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class DoctorRepository implements AbstractRepository<Doctor> {

    private final HashMap<String, Doctor> doctors = new HashMap<>();
    @Override
    public void save(Doctor doctor) {
        if (doctor.getStaffID() == null || doctor.getStaffID().isEmpty()) {
            doctor.setStaffID(UUID.randomUUID().toString());
        }
        doctors.put(doctor.getStaffID(), doctor);
    }

    @Override
    public Doctor findById(String id) {
        return doctors.get(id);
    }

    @Override
    public void delete(Doctor doctor) {
        doctors.remove(doctor.getStaffID());
    }

    @Override
    public List<Doctor> findAll() {
        return new ArrayList<>(doctors.values());
    }
}