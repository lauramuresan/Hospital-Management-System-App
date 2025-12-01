package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;
import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Department;

public class DepartmentMapper {

    public static DepartmentEntity toEntity(Department domain) {
        if (domain == null) return null;
        DepartmentEntity entity = new DepartmentEntity();

        entity.setId(domain.getDepartmentID() != null ? MapperUtils.parseLong(domain.getDepartmentID()) : null);
        entity.setDepartmentName(domain.getDepartmentName());
        entity.setHospital(MapperUtils.createEntityProxy(HospitalEntity.class, domain.getHospitalID()));

        return entity;
    }

    public static Department toDomain(DepartmentEntity entity) {
        if (entity == null) return null;
        Department domain = new Department();

        domain.setDepartmentID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setDepartmentName(entity.getDepartmentName());

        if (entity.getHospital() != null && entity.getHospital().getId() != null)
            domain.setHospitalID(String.valueOf(entity.getHospital().getId()));

        return domain;
    }
}