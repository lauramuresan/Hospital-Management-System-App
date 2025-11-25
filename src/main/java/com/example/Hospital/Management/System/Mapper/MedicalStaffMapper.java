package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaff;
import com.example.Hospital.Management.System.Model.DBModel.MedicalStaffEntity;
import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;

public abstract class MedicalStaffMapper {

    protected static <D extends MedicalStaff, E extends MedicalStaffEntity> E mapBaseToEntity(D domain, E entity) {
        entity.setId(domain.getStaffID() != null ? MapperUtils.parseLong(domain.getStaffID()) : null);
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
        return domain;
    }
}
