package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.DBModel.*;
import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaffAppointment;

public class MedicalStaffAppointmentMapper {

    public static MedicalStaffAppointmentEntity mapDomainToEntity(MedicalStaffAppointment domain, MedicalStaffAppointmentEntity entity) {
        if (domain == null) return null;
        if (entity == null) entity = new MedicalStaffAppointmentEntity();

        // 1. Setarea ID-ului alocării (CRITIC pentru UPDATE)
        String domainId = domain.getMedicalStaffAppointmentID();
        if (domainId != null && !domainId.isBlank()) {
            try {
                // Dacă ID-ul este valid, îl setăm pe Entitate
                entity.setId(Long.parseLong(domainId));
            } catch (NumberFormatException e) {
                // Dacă nu e valid, lăsăm entity.id = null, forțând un INSERT (sau lăsăm să cadă validarea)
                entity.setId(null);
            }
        }

        // 2. Setarea relațiilor (Appointment, Doctor, Nurse) folosind Proxy
        // Folosim ID-urile de tip String din Domain pentru a crea Proxy-uri JPA.
        entity.setAppointment(MapperUtils.createEntityProxy(AppointmentEntity.class, domain.getAppointmentID()));

        // Presupunem că ID-ul din domain.getMedicalStaffID() este ID-ul Doctorului (conform logicii din adaptor)
        entity.setDoctor(MapperUtils.createEntityProxy(DoctorEntity.class, domain.getMedicalStaffID()));
        entity.setNurse(MapperUtils.createEntityProxy(NurseEntity.class, null));

        return entity;
    }

    public static MedicalStaffAppointment toDomain(MedicalStaffAppointmentEntity entity) {
        if (entity == null) return null;

        MedicalStaffAppointment domain = new MedicalStaffAppointment();

        // ID-ul Alocării (String)
        domain.setMedicalStaffAppointmentID(entity.getId() != null ? String.valueOf(entity.getId()) : null);

        // ID-ul Programării (String)
        if (entity.getAppointment() != null && entity.getAppointment().getId() != null) {
            domain.setAppointmentID(String.valueOf(entity.getAppointment().getId()));
        }

        // ID-ul Personalului Medical (Doctor sau Nurse)
        if (entity.getDoctor() != null && entity.getDoctor().getId() != null) {
            domain.setMedicalStaffID(String.valueOf(entity.getDoctor().getId()));
        } else if (entity.getNurse() != null && entity.getNurse().getId() != null) {
            domain.setMedicalStaffID(String.valueOf(entity.getNurse().getId()));
        }

        return domain;
    }
}