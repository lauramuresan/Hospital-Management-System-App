package com.example.Hospital.Management.System.Repository.InMemory;
import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import org.springframework.stereotype.Repository;
@Repository("appointmentInMemory")
public class AppointmentInMemoryRepository extends InMemoryRepository<Appointment> {
    @Override
    protected String getId(Appointment appointment) {
        return appointment.getAppointmentID();
    }
    @Override
    protected void setId(Appointment appointment, String id) {
        appointment.setAppointmentID(id);
    }
}
