package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Nurse;
import com.example.Hospital.Management.System.Model.DBModel.NurseEntity;

public class NurseMapper extends MedicalStaffMapper {

    public static NurseEntity toEntity(Nurse domain) {
        if (domain == null) return null;
        NurseEntity entity = new NurseEntity();
        mapBaseToEntity(domain, entity);
        entity.setNurseCategory(domain.getQualificationLevel());
        return entity;
    }

    public static Nurse toDomain(NurseEntity entity) {
        if (entity == null) return null;
        Nurse domain = new Nurse();
        mapBaseToDomain(entity, domain);
        domain.setQualificationLevel(entity.getNurseCategory());
        return domain;
    }
}