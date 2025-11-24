package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaff;
import com.example.Hospital.Management.System.Model.DBModel.MedicalStaffEntity;
import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;

public abstract class MedicalStaffMapper {

    protected static <D extends MedicalStaff, E extends MedicalStaffEntity> E mapBaseToEntity(D domain, E entity) {
        if (domain.getStaffID() != null) {
            try { entity.setId(Long.valueOf(domain.getStaffID())); } catch (NumberFormatException e) {}
        }
        entity.setStaffName(domain.getStaffName());
        entity.setStaffEmail(domain.getStaffEmail());

        // Maparea Department (Proxy)
        if (domain.getDepartmentID() != null) {
            DepartmentEntity departmentProxy = new DepartmentEntity();
            try { departmentProxy.setId(Long.valueOf(domain.getDepartmentID())); } catch (NumberFormatException e) { return null; }
            entity.setDepartment(departmentProxy);
        }

        return entity;
    }

    protected static <D extends MedicalStaff, E extends MedicalStaffEntity> D mapBaseToDomain(E entity, D domain) {
        domain.setStaffID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setStaffName(entity.getStaffName());
        domain.setStaffEmail(entity.getStaffEmail());

        if (entity.getDepartment() != null && entity.getDepartment().getId() != null) {
            // Presupunând că POJO-ul MedicalStaff are un setDepartmentID(String)
            domain.setDepartmentID(String.valueOf(entity.getDepartment().getId()));
        }

        return domain;
    }
}