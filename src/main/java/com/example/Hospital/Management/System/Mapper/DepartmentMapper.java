package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;
import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;
import com.example.Hospital.Management.System.Mapper.MapperUtils; // Presupunând că folosiți clasa utilitară

public class DepartmentMapper {

    // Metodă ajutătoare pentru a converti de la Domeniu la Entitate (fără a seta HospitalEntity)
    public static DepartmentEntity toEntity(Department domain) {
        if (domain == null) return null;
        DepartmentEntity entity = new DepartmentEntity();

        if (domain.getDepartmentID() != null) {
            // Nu mai folosim try-catch, presupunând că ID-ul este validat de Adaptor sau Controller
            entity.setId(MapperUtils.parseLong(domain.getDepartmentID()));
        }
        entity.setDepartmentName(domain.getDepartmentName());

        // Atenție: NU MAI SETĂM SPITALUL AICI! Spitalul este setat în Adaptor.

        return entity;
    }

    public static Department toDomain(DepartmentEntity entity) {
        if (entity == null) return null;
        Department domain = new Department();

        domain.setDepartmentID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setDepartmentName(entity.getDepartmentName());

        // Verificăm dacă HospitalEntity nu este null și are ID înainte de a-l extrage
        if (entity.getHospital() != null && entity.getHospital().getId() != null) {
            domain.setHospitalID(String.valueOf(entity.getHospital().getId()));
        }
        return domain;
    }
}