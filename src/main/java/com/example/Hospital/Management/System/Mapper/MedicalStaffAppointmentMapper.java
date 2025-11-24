package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.DBModel.AppointmentEntity;
import com.example.Hospital.Management.System.Model.DBModel.DoctorEntity;
import com.example.Hospital.Management.System.Model.DBModel.NurseEntity;
import com.example.Hospital.Management.System.Model.DBModel.MedicalStaffAppointmentEntity;

public class MedicalStaffAppointmentMapper {

    // Metodă statică: OK
    public static MedicalStaffAppointmentEntity createEntityFromIds(
            String appointmentId, String doctorId, String nurseId) {

        MedicalStaffAppointmentEntity entity = new MedicalStaffAppointmentEntity();

        if (appointmentId != null) {
            AppointmentEntity appointmentProxy = new AppointmentEntity();
            try { appointmentProxy.setId(Long.valueOf(appointmentId)); } catch (NumberFormatException e) { return null; }
            entity.setAppointment(appointmentProxy);
        }

        if (doctorId != null) {
            DoctorEntity doctorProxy = new DoctorEntity();
            try { doctorProxy.setId(Long.valueOf(doctorId)); } catch (NumberFormatException e) { return null; }
            entity.setDoctor(doctorProxy);
        }

        if (nurseId != null) {
            NurseEntity nurseProxy = new NurseEntity();
            try { nurseProxy.setId(Long.valueOf(nurseId)); } catch (NumberFormatException e) { return null; }
            entity.setNurse(nurseProxy);
        }

        return entity;
    }

}