package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.MedicalStaffAppointment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class MedicalStaffAppointmentRepository implements AbstractRepository<MedicalStaffAppointment> {
    private final HashMap<String, MedicalStaffAppointment> medicalStaffAppointments = new HashMap<>();
    @Override
    public void save(MedicalStaffAppointment medicalStaffAppointment) {
        if(medicalStaffAppointment.getMedicalStaffID() == null || medicalStaffAppointment.getAppointmentID().isEmpty()) {
            medicalStaffAppointment.setMedicalStaffAppointmentID(UUID.randomUUID().toString());
        }
        medicalStaffAppointments.put(medicalStaffAppointment.getMedicalStaffAppointmentID(), medicalStaffAppointment);
    }
    @Override
    public void delete(MedicalStaffAppointment medicalStaffAppointment) {
        medicalStaffAppointments.remove(medicalStaffAppointment.getMedicalStaffAppointmentID());
    }

    @Override
    public MedicalStaffAppointment findById(String id) {
        return medicalStaffAppointments.get(id);
    }

    @Override
    public List<MedicalStaffAppointment> findAll() {
        return new ArrayList<>(medicalStaffAppointments.values());
    }
}
