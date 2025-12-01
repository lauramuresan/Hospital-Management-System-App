package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.DBModel.*;
import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaffAppointment;

public class MedicalStaffAppointmentMapper {

    public static MedicalStaffAppointmentEntity createEntityFromIds(String appointmentId, String doctorId, String nurseId) {
        MedicalStaffAppointmentEntity entity = new MedicalStaffAppointmentEntity();
        entity.setAppointment(MapperUtils.createEntityProxy(AppointmentEntity.class, appointmentId));
        entity.setDoctor(MapperUtils.createEntityProxy(DoctorEntity.class, doctorId));
        entity.setNurse(MapperUtils.createEntityProxy(NurseEntity.class, nurseId));
        return entity;
    }

    public static MedicalStaffAppointment toDomain(MedicalStaffAppointmentEntity entity) {
        if (entity == null) return null;

        MedicalStaffAppointment domain = new MedicalStaffAppointment();
        domain.setMedicalStaffAppointmentID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        if (entity.getAppointment() != null && entity.getAppointment().getId() != null) {
            domain.setAppointmentID(String.valueOf(entity.getAppointment().getId()));
        }

        if (entity.getDoctor() != null && entity.getDoctor().getId() != null) {
            domain.setMedicalStaffID(String.valueOf(entity.getDoctor().getId()));
        } else if (entity.getNurse() != null && entity.getNurse().getId() != null) {
            domain.setMedicalStaffID(String.valueOf(entity.getNurse().getId()));
        }

        return domain;
    }
}