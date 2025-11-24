package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import com.example.Hospital.Management.System.Model.DBModel.DoctorEntity;

public class DoctorMapper extends MedicalStaffMapper {

    // Metoda toEntity este deja statică (OK)
    public static DoctorEntity toEntity(Doctor domain) {
        if (domain == null) return null;
        DoctorEntity entity = new DoctorEntity();

        // Presupunând că mapBaseToEntity este statică
        mapBaseToEntity(domain, entity);

        entity.setLicenseNumber(domain.getLicenseNumber());
        entity.setMedicalSpeciality(domain.getMedicalSpeciality());

        return entity;
    }

    // CORECȚIE: Adăugăm static
    public static Doctor toDomain(DoctorEntity entity) {
        if (entity == null) return null;
        Doctor domain = new Doctor();

        // Presupunând că mapBaseToDomain este statică
        mapBaseToDomain(entity, domain);

        domain.setLicenseNumber(entity.getLicenseNumber());
        domain.setMedicalSpeciality(entity.getMedicalSpeciality());

        return domain;
    }
}