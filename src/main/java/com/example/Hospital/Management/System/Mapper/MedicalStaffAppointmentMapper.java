package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.DBModel.*;
import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaffAppointment;
import org.springframework.stereotype.Component;

@Component
public class MedicalStaffAppointmentMapper {

    private MedicalStaffAppointmentEntity mapDomainToEntity(MedicalStaffAppointment domain, MedicalStaffAppointmentEntity entity) {
        if (domain == null) return null;
        if (entity == null) entity = new MedicalStaffAppointmentEntity();

        String domainId = domain.getMedicalStaffAppointmentID();
        if (domainId != null && !domainId.isBlank()) {
            try {
                entity.setId(Long.parseLong(domainId));
            } catch (NumberFormatException e) {
                entity.setId(null);
            }
        }

        if (domain.getAppointmentID() != null) {
            entity.setAppointment(MapperUtils.createEntityProxy(AppointmentEntity.class, domain.getAppointmentID()));
        }
        return entity;
    }

    public MedicalStaffAppointmentEntity toEntity(MedicalStaffAppointment domain) {
        return mapDomainToEntity(domain, new MedicalStaffAppointmentEntity());
    }

    public MedicalStaffAppointment toDomain(MedicalStaffAppointmentEntity entity) {
        if (entity == null) return null;

        MedicalStaffAppointment domain = new MedicalStaffAppointment();

        // 1. ID Alocare
        domain.setMedicalStaffAppointmentID(entity.getId() != null ? String.valueOf(entity.getId()) : null);

        // 2. ID Programare
        if (entity.getAppointment() != null && entity.getAppointment().getId() != null) {
            domain.setAppointmentID(String.valueOf(entity.getAppointment().getId()));
        }

        // 3. ID Personal Medical
        // Logica unificată: Verificăm Doctorul, apoi Asistentul
        if (entity.getDoctor() != null && entity.getDoctor().getId() != null) {
            domain.setMedicalStaffID(String.valueOf(entity.getDoctor().getId()));
        }
        else if (entity.getNurse() != null && entity.getNurse().getId() != null) {
            domain.setMedicalStaffID(String.valueOf(entity.getNurse().getId()));
        }

        return domain;
    }
}