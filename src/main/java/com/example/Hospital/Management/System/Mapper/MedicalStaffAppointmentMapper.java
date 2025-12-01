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

    /**
     * Mapează o entitate JPA la obiectul de domeniu (DTO).
     * @param entity Entitatea JPA cu relațiile Appointment, Doctor și Nurse încărcate (EAGER).
     * @return Obiectul de domeniu MedicalStaffAppointment.
     */
    public static MedicalStaffAppointment toDomain(MedicalStaffAppointmentEntity entity) {
        if (entity == null) return null;

        MedicalStaffAppointment domain = new MedicalStaffAppointment();

        // 1. ID-ul Alocării (PrimaryKey din tabelul de alocare)
        domain.setMedicalStaffAppointmentID(entity.getId() != null ? String.valueOf(entity.getId()) : null);

        // 2. ID-ul Programării
        if (entity.getAppointment() != null && entity.getAppointment().getId() != null) {
            domain.setAppointmentID(String.valueOf(entity.getAppointment().getId()));
        }

        // 3. ID-ul Personalului Medical (Identificăm dacă este Doctor sau Asistentă)
        if (entity.getDoctor() != null && entity.getDoctor().getId() != null) {
            // Dacă este Doctor, folosim ID-ul Doctorului
            domain.setMedicalStaffID(String.valueOf(entity.getDoctor().getId()));
        } else if (entity.getNurse() != null && entity.getNurse().getId() != null) {
            // Dacă este Asistentă, folosim ID-ul Asistentei
            domain.setMedicalStaffID(String.valueOf(entity.getNurse().getId()));
        }

        return domain;
    }
}