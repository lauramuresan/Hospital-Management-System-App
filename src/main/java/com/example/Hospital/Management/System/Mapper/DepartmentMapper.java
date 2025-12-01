package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;
// import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity; // Nu e necesar aici
// import com.example.Hospital.Management.System.Mapper.MapperUtils; // Presupunând că e deja importată

import java.util.stream.Collectors;

public class DepartmentMapper {

    public static DepartmentEntity toEntity(Department domain) {
        if (domain == null) return null;
        DepartmentEntity entity = new DepartmentEntity();

        String idString = domain.getDepartmentID();

        // 🟢 CORECȚIA CRITICĂ: Verifică String-ul gol ("")
        if (idString != null && !idString.trim().isEmpty()) {
            try {
                // Dacă avem un String non-gol, încercăm să-l mapăm la Long
                entity.setId(MapperUtils.parseLong(idString));
            } catch (NumberFormatException e) {
                // Dacă nu este un număr valid, lăsăm ID-ul null.
                entity.setId(null);
            }
        } else {
            // Dacă idString este null SAU gol (""), forțăm ID-ul entității să fie null (pentru INSERT)
            entity.setId(null);
        }
        // END CORECȚIE

        entity.setDepartmentName(domain.getDepartmentName());

        // Atenție: Hospital-ul ar trebui setat în Adaptor pe baza domain.getHospitalID()
        // Acest Mapper NU are cod pentru a seta HospitalEntity, ceea ce este corect

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