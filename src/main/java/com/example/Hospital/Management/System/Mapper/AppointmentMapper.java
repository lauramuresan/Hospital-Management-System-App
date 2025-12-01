package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Model.DBModel.AppointmentEntity;
import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;
import com.example.Hospital.Management.System.Model.DBModel.RoomEntity; // NOU: Import necesar

public class AppointmentMapper {

    /**
     * Mapează de la modelul de domeniu (Appointment) la entitatea JPA (AppointmentEntity).
     * Se folosește de proxy-uri pentru a seta doar ID-urile străine (FK).
     */
    public static AppointmentEntity toEntity(Appointment domain) {
        if (domain == null) return null;

        AppointmentEntity entity = new AppointmentEntity();

        entity.setId(domain.getAppointmentID() != null ? MapperUtils.parseLong(domain.getAppointmentID()) : null);
        entity.setAdmissionDate(domain.getAdmissionDate());
        entity.setStatus(domain.getStatus());

        // Mapează PatientID la PatientEntity
        entity.setPatient(MapperUtils.createEntityProxy(PatientEntity.class, domain.getPatientID()));

        // ✅ CORECȚIE ESENȚIALĂ: Mapează RoomID la RoomEntity
        entity.setRoom(MapperUtils.createEntityProxy(RoomEntity.class, domain.getRoomID()));

        return entity;
    }

    /**
     * Mapează de la entitatea JPA (AppointmentEntity) la modelul de domeniu (Appointment).
     */
    public static Appointment toDomain(AppointmentEntity entity) {
        if (entity == null) return null;

        Appointment domain = new Appointment();

        domain.setAppointmentID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setAdmissionDate(entity.getAdmissionDate());
        domain.setStatus(entity.getStatus());

        // Mapează PatientEntity la PatientID
        if (entity.getPatient() != null && entity.getPatient().getId() != null)
            domain.setPatientID(String.valueOf(entity.getPatient().getId()));

        // ✅ CORECȚIE ESENȚIALĂ: Mapează RoomEntity la RoomID
        if (entity.getRoom() != null && entity.getRoom().getId() != null)
            domain.setRoomID(String.valueOf(entity.getRoom().getId()));

        // Atenție: Maparea staffList este gestionată în alte adaptoare/servicii sau lăsată la lazy loading
        // Dacă staffList trebuie mapată aici, ar fi necesară o metodă MedicalStaffAppointmentMapper.
        // Presupunând că se folosește lista goală din constructorul Appointment.

        return domain;
    }
}