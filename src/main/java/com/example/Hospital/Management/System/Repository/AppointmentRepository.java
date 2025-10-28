package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.Appointment;
import org.springframework.stereotype.Repository;
@Repository
public class AppointmentRepository extends InMemoryRepository<Appointment> {
    @Override
    protected String getId(Appointment appointment) {
        return appointment.getAppointmentID();
    }
    @Override
    protected void setId(Appointment appointment, String id) {
        appointment.setAppointmentID(id);
    }
}
