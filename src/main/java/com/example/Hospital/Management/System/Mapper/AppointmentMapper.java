package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Model.DBModel.AppointmentEntity;
import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;

public class AppointmentMapper {

    public static AppointmentEntity toEntity(Appointment domain) {
        if (domain == null) return null;
        AppointmentEntity entity = new AppointmentEntity();
        entity.setId(domain.getAppointmentID() != null ? MapperUtils.parseLong(domain.getAppointmentID()) : null);
        entity.setAppointmentDateTime(domain.getAdmissionDate());
        entity.setStatus(domain.getStatus());
        entity.setPatient(MapperUtils.createEntityProxy(PatientEntity.class, domain.getPatientID()));
        return entity;
    }

    public static Appointment toDomain(AppointmentEntity entity) {
        if (entity == null) return null;
        Appointment domain = new Appointment();
        domain.setAppointmentID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setAdmissionDate(entity.getAppointmentDateTime());
        domain.setStatus(entity.getStatus());
        if (entity.getPatient() != null && entity.getPatient().getId() != null)
            domain.setPatientID(String.valueOf(entity.getPatient().getId()));
        return domain;
    }
}
