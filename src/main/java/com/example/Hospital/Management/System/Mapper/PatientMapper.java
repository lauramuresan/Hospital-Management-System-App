package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;

public class PatientMapper {

    // Metoda toEntity este deja statică (OK)
    public static PatientEntity toEntity(Patient domain) {
        if (domain == null) return null;
        PatientEntity entity = new PatientEntity();

        if (domain.getPatientID() != null) {
            try { entity.setId(Long.valueOf(domain.getPatientID())); } catch (NumberFormatException e) {}
        }
        entity.setPatientName(domain.getPatientName());
        entity.setPacientEmail(domain.getPacientEmail());
        entity.setPatientBirthDate(domain.getPatientBirthDate());

        return entity;
    }

    // CORECȚIE: Metoda trebuie să fie statică
    public static Patient toDomain(PatientEntity entity) {
        if (entity == null) return null;
        Patient domain = new Patient();

        domain.setPatientID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setPatientName(entity.getPatientName());
        domain.setPacientEmail(entity.getPacientEmail());
        domain.setPatientBirthDate(entity.getPatientBirthDate());

        return domain;
    }
}