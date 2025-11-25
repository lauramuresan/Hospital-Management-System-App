package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;

import java.util.stream.Collectors;

public class HospitalMapper {

    public static HospitalEntity toEntity(Hospital domain) {
        if (domain == null) return null;
        HospitalEntity entity = new HospitalEntity();
        entity.setId(domain.getHospitalID() != null ? MapperUtils.parseLong(domain.getHospitalID()) : null);
        entity.setHospitalName(domain.getHospitalName());
        entity.setCity(domain.getCity());
        entity.setDepartments(MapperUtils.mapListWithParent(domain.getDepartments(), DepartmentMapper::toEntity, d -> d.setHospital(entity)));
        return entity;
    }

    public static Hospital toDomain(HospitalEntity entity) {
        if (entity == null) return null;
        Hospital domain = new Hospital();
        domain.setHospitalID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setHospitalName(entity.getHospitalName());
        domain.setCity(entity.getCity());
        if (entity.getDepartments() != null)
            domain.setDepartments(entity.getDepartments().stream().map(DepartmentMapper::toDomain).collect(Collectors.toList()));
        return domain;
    }
}
