package com.example.Hospital.Management.System.Repository;

import com.example.Hospital.Management.System.Model.MedicalStaff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MedicalStaffRepository implements AbstractRepository<MedicalStaff> {
    private final HashMap<String, MedicalStaff> medicalStaffs = new HashMap<>();

    @Override
    public void save(MedicalStaff medicalStaff) {
        if(medicalStaff.getStaffID()==null || medicalStaff.getStaffID().isEmpty())
            medicalStaff.setStaffID(UUID.randomUUID().toString());
        medicalStaffs.put(medicalStaff.getStaffID(), medicalStaff);
    }
    @Override
    public MedicalStaff findById(String id) {
        return medicalStaffs.get(id);
    }
    @Override
    public void delete(MedicalStaff medicalStaff) {
        medicalStaffs.remove(medicalStaff.getStaffID());
    }
    @Override
    public List<MedicalStaff> findAll(){
        return new ArrayList<>(medicalStaffs.values());
    }
}
