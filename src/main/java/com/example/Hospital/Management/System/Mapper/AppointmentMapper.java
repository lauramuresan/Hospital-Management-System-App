package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Model.DBModel.AppointmentEntity;
import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;
import com.example.Hospital.Management.System.Model.DBModel.RoomEntity; // NOU: Import necesar

public class AppointmentMapper {

    public static AppointmentEntity toEntity(Appointment domain) {
        if (domain == null) return null;

        AppointmentEntity entity = new AppointmentEntity();

        entity.setId(domain.getAppointmentID() != null ? MapperUtils.parseLong(domain.getAppointmentID()) : null);
        entity.setAdmissionDate(domain.getAdmissionDate());
        entity.setStatus(domain.getStatus());
        entity.setPatient(MapperUtils.createEntityProxy(PatientEntity.class, domain.getPatientID()));
        entity.setRoom(MapperUtils.createEntityProxy(RoomEntity.class, domain.getRoomID()));

        return entity;
    }

    public static Appointment toDomain(AppointmentEntity entity) {
        if (entity == null) return null;

        Appointment domain = new Appointment();

        domain.setAppointmentID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setAdmissionDate(entity.getAdmissionDate());
        domain.setStatus(entity.getStatus());


        if (entity.getPatient() != null && entity.getPatient().getId() != null)
            domain.setPatientID(String.valueOf(entity.getPatient().getId()));

        if (entity.getRoom() != null && entity.getRoom().getId() != null)
            domain.setRoomID(String.valueOf(entity.getRoom().getId()));

        return domain;
    }
}