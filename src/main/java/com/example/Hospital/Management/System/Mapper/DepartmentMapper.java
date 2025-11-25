package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;
import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;

public class DepartmentMapper {
    public static DepartmentEntity toEntity(Department domain) {
        if (domain == null) return null;
        DepartmentEntity entity = new DepartmentEntity();

        if (domain.getDepartmentID() != null) {
            try { entity.setId(Long.valueOf(domain.getDepartmentID())); } catch (NumberFormatException e) {}
        }
        entity.setDepartmentName(domain.getDepartmentName());

        if (domain.getHospitalID() != null) {
            HospitalEntity hospitalProxy = new HospitalEntity();
            try { hospitalProxy.setId(Long.valueOf(domain.getHospitalID())); } catch (NumberFormatException e) { return null; }
            entity.setHospital(hospitalProxy);
        }

        return entity;
    }

    public static Department toDomain(DepartmentEntity entity) {
        if (entity == null) return null;
        Department domain = new Department();

        domain.setDepartmentID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setDepartmentName(entity.getDepartmentName());
        if (entity.getHospital() != null && entity.getHospital().getId() != null) {
            domain.setHospitalID(String.valueOf(entity.getHospital().getId()));
        }
        return domain;
    }
}