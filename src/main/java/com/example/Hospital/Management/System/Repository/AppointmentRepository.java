package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.Appointment;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class AppointmentRepository implements AbstractRepository<Appointment> {
    private final HashMap<String, Appointment> appointments = new HashMap<>();
    @Override
    public void save(Appointment appointment) {
        if (appointment.getAppointmentID() == null || appointment.getAppointmentID().isEmpty()) {
            appointment.setAppointmentID(UUID.randomUUID().toString());
        }
        appointments.put(appointment.getAppointmentID(), appointment);
    }
    @Override
    public Appointment findById(String id) {
        return appointments.get(id);
    }
    public void delete(Appointment appointment) {
        appointments.remove(appointment.getAppointmentID());
    }
    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(appointments.values());
    }
}
