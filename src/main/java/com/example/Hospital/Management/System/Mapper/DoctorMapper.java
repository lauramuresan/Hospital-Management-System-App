package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import com.example.Hospital.Management.System.Model.DBModel.DoctorEntity;

public class DoctorMapper extends MedicalStaffMapper {

    public static DoctorEntity toEntity(Doctor domain) {
        if (domain == null) return null;
        DoctorEntity entity = new DoctorEntity();
        mapBaseToEntity(domain, entity); // Folosește logica de ID corectată
        entity.setLicenseNumber(domain.getLicenseNumber());
        entity.setMedicalSpeciality(domain.getMedicalSpeciality());
        return entity;
    }

    public static Doctor toDomain(DoctorEntity entity) {
        if (entity == null) return null;
        Doctor domain = new Doctor();
        mapBaseToDomain(entity, domain);
        domain.setLicenseNumber(entity.getLicenseNumber());
        domain.setMedicalSpeciality(entity.getMedicalSpeciality());
        return domain;
    }
}