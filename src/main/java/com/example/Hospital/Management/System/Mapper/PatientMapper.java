package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;

import java.util.Collections;
import java.util.stream.Collectors;

public class PatientMapper {

    public static PatientEntity toEntity(Patient domain) {
        if (domain == null) return null;
        PatientEntity entity = new PatientEntity();
        String idString = domain.getPatientID();
        if (idString != null && !idString.trim().isEmpty()) {
            try {
                entity.setId(MapperUtils.parseLong(idString));
            } catch (NumberFormatException e) {
                entity.setId(null);
            }
        } else {
            entity.setId(null);
        }
        entity.setPatientName(domain.getPatientName());
        entity.setPacientEmail(domain.getPacientEmail());
        entity.setPatientBirthDate(domain.getPatientBirthDate());
        return entity;
    }

    public static Patient toDomain(PatientEntity entity) {
        if (entity == null) return null;
        Patient domain = new Patient();
        domain.setPatientID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setPatientName(entity.getPatientName());
        domain.setPacientEmail(entity.getPacientEmail());
        domain.setPatientBirthDate(entity.getPatientBirthDate());

        // --- FIX: Creăm obiecte Appointment "sumare" (doar cu ID) ---
        if (entity.getAppointmentList() != null) {
            domain.setAppointmentList(entity.getAppointmentList().stream()
                    .map(appEntity -> {
                        Appointment app = new Appointment();
                        app.setAppointmentID(String.valueOf(appEntity.getId()));
                        return app;
                    })
                    .collect(Collectors.toList()));
        } else {
            domain.setAppointmentList(Collections.emptyList());
        }

        return domain;
    }
}