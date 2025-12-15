package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import com.example.Hospital.Management.System.Model.DBModel.DoctorEntity;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper extends MedicalStaffMapper {

    public DoctorEntity toEntity(Doctor domain) {
        if (domain == null) return null;
        DoctorEntity entity = new DoctorEntity();

        mapBaseToEntity(domain, entity);

        entity.setLicenseNumber(domain.getLicenseNumber());
        entity.setMedicalSpeciality(domain.getMedicalSpeciality());
        return entity;
    }

    public Doctor toDomain(DoctorEntity entity) {
        if (entity == null) return null;
        Doctor domain = new Doctor();

        mapBaseToDomain(entity, domain);

        domain.setLicenseNumber(entity.getLicenseNumber());
        domain.setMedicalSpeciality(entity.getMedicalSpeciality());
        return domain;
    }
}