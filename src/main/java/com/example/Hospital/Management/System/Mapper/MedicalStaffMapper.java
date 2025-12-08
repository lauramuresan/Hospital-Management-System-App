package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaff;
import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Model.DBModel.MedicalStaffEntity;
import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;

import java.util.Collections;
import java.util.stream.Collectors;

public abstract class MedicalStaffMapper {

    protected static <D extends MedicalStaff, E extends MedicalStaffEntity> E mapBaseToEntity(D domain, E entity) {
        String idString = domain.getStaffID();
        if (idString != null && !idString.trim().isEmpty()) {
            try {
                entity.setId(MapperUtils.parseLong(idString));
            } catch (NumberFormatException e) {
                entity.setId(null);
            }
        } else {
            entity.setId(null);
        }
        entity.setStaffName(domain.getStaffName());
        entity.setStaffEmail(domain.getStaffEmail());
        entity.setDepartment(MapperUtils.createEntityProxy(DepartmentEntity.class, domain.getDepartmentID()));
        return entity;
    }

    protected static <D extends MedicalStaff, E extends MedicalStaffEntity> D mapBaseToDomain(E entity, D domain) {
        domain.setStaffID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setStaffName(entity.getStaffName());
        domain.setStaffEmail(entity.getStaffEmail());
        if (entity.getDepartment() != null && entity.getDepartment().getId() != null)
            domain.setDepartmentID(String.valueOf(entity.getDepartment().getId()));

        // --- FIX: Creăm obiecte Appointment "sumare" (doar cu ID) ---
        if (entity.getMedicalStaffAppointments() != null) {
            domain.setAppointments(entity.getMedicalStaffAppointments().stream()
                    .map(msa -> {
                        Appointment app = new Appointment();
                        if (msa.getAppointment() != null) {
                            app.setAppointmentID(String.valueOf(msa.getAppointment().getId()));
                        }
                        return app;
                    })
                    .collect(Collectors.toList()));
        } else {
            domain.setAppointments(Collections.emptyList());
        }

        return domain;
    }
}