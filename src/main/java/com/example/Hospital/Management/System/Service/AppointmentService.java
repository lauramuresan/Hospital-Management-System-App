package com.example.Hospital.Management.System.Service;
import com.example.Hospital.Management.System.Model.Appointment;
import com.example.Hospital.Management.System.Repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    public void addAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }
    public void removeAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment != null) {
            appointmentRepository.delete(appointment);
        }
    }
    public Appointment getAppointmentById(String id) {
        return appointmentRepository.findById(id);
    }
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
