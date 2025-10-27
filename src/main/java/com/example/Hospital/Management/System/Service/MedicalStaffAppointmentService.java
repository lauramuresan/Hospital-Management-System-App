package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.MedicalStaffAppointment;
import com.example.Hospital.Management.System.Repository.MedicalStaffAppointmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalStaffAppointmentService {
    private final MedicalStaffAppointmentRepository medicalStaffAppointmentRepository;
    public MedicalStaffAppointmentService(MedicalStaffAppointmentRepository medicalStaffAppointmentRepository) {
        this.medicalStaffAppointmentRepository = medicalStaffAppointmentRepository;
    }
    public void addMedicalStaffAppointment(MedicalStaffAppointment appointment) {
        medicalStaffAppointmentRepository.save(appointment);
    }
    public void removeMedicalStaffAppointment(String appointmentId) {
        MedicalStaffAppointment appointment = medicalStaffAppointmentRepository.findById(appointmentId);
        if (appointment != null) medicalStaffAppointmentRepository.delete(appointment);
    }
    public MedicalStaffAppointment getMedicalStaffAppointmentById(String id) {
        return medicalStaffAppointmentRepository.findById(id);
    }
    public List<MedicalStaffAppointment> getAllMedicalStaffAppointments() {
        return medicalStaffAppointmentRepository.findAll();
    }
}