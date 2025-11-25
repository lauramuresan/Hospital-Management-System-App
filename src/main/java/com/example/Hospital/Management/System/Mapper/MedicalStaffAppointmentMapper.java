package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.DBModel.*;

public class MedicalStaffAppointmentMapper {

    public static MedicalStaffAppointmentEntity createEntityFromIds(String appointmentId, String doctorId, String nurseId) {
        MedicalStaffAppointmentEntity entity = new MedicalStaffAppointmentEntity();
        entity.setAppointment(MapperUtils.createEntityProxy(AppointmentEntity.class, appointmentId));
        entity.setDoctor(MapperUtils.createEntityProxy(DoctorEntity.class, doctorId));
        entity.setNurse(MapperUtils.createEntityProxy(NurseEntity.class, nurseId));
        return entity;
    }
}
