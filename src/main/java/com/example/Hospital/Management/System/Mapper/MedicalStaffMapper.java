package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaff;
import com.example.Hospital.Management.System.Model.DBModel.MedicalStaffEntity;
import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;

public abstract class MedicalStaffMapper {

    protected static <D extends MedicalStaff, E extends MedicalStaffEntity> E mapBaseToEntity(D domain, E entity) {

        String idString = domain.getStaffID();

        // CORECȚIE ID: Asigură că ID-ul este NULL pentru INSERT-uri
        if (idString != null && !idString.trim().isEmpty()) {
            try {
                entity.setId(MapperUtils.parseLong(idString));
            } catch (NumberFormatException e) {
                entity.setId(null);
            }
        } else {
            entity.setId(null);
        }
        // END CORECȚIE

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