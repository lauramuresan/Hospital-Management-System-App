package com.example.Hospital.Management.System.Repository.InFile;

import com.example.Hospital.Management.System.Model.Appointment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository("appointmentInFile")
public class AppointmentInFileRepository extends InFileRepository<Appointment> {

    public AppointmentInFileRepository(ObjectMapper mapper) {
        super(mapper, "./data", "appointments.json");
    }

    @Override
    protected String getId(Appointment appointment) {
        return appointment.getAppointmentID();
    }

    @Override
    protected void setId(Appointment appointment, String id) {
        appointment.setAppointmentID(id);
    }
}
