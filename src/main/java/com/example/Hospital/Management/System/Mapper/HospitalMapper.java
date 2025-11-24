package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;
import java.util.stream.Collectors;

public class HospitalMapper {

    // Metoda toEntity este deja statică (OK)
    public static HospitalEntity toEntity(Hospital domain) {
        if (domain == null) return null;
        HospitalEntity entity = new HospitalEntity();

        if (domain.getHospitalID() != null) {
            try { entity.setId(Long.valueOf(domain.getHospitalID())); } catch (NumberFormatException e) {}
        }
        entity.setHospitalName(domain.getHospitalName());
        entity.setCity(domain.getCity());

        if (domain.getDepartments() != null) {
            entity.setDepartments(domain.getDepartments().stream()
                    .map(DepartmentMapper::toEntity) // Presupunând că toEntity este statică
                    .peek(d -> d.setHospital(entity))
                    .collect(Collectors.toList()));
        }

        return entity;
    }

    // CORECȚIE: Adăugăm static
    public static Hospital toDomain(HospitalEntity entity) { // <-- CORECTAT
        if (entity == null) return null;
        Hospital domain = new Hospital();

        domain.setHospitalID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setHospitalName(entity.getHospitalName());
        domain.setCity(entity.getCity());

        if (entity.getDepartments() != null) {
            domain.setDepartments(entity.getDepartments().stream()
                    .map(DepartmentMapper::toDomain) // Presupunând că toDomain este statică
                    .collect(Collectors.toList()));
        }

        return domain;
    }
}