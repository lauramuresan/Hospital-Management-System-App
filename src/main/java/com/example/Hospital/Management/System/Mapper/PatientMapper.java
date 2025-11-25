package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;

public class PatientMapper {

    public static PatientEntity toEntity(Patient domain) {
        if (domain == null) return null;
        PatientEntity entity = new PatientEntity();
        entity.setId(domain.getPatientID() != null ? MapperUtils.parseLong(domain.getPatientID()) : null);
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
        return domain;
    }
}
