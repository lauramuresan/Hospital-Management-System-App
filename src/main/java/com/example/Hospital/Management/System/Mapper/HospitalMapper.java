package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class HospitalMapper {

    private final DepartmentMapper departmentMapper;
    private final RoomMapper roomMapper;

    public HospitalMapper(DepartmentMapper departmentMapper, RoomMapper roomMapper) {
        this.departmentMapper = departmentMapper;
        this.roomMapper = roomMapper;
    }

    public HospitalEntity toEntity(Hospital domain) {
        if (domain == null) return null;
        HospitalEntity entity = new HospitalEntity();
        String idString = domain.getHospitalID();

        if (idString != null && !idString.trim().isEmpty()) {
            try {
                entity.setId(MapperUtils.parseLong(idString));
            } catch (NumberFormatException e) {
                entity.setId(null);
            }
        } else {
            entity.setId(null);
        }
        entity.setHospitalName(domain.getHospitalName());
        entity.setCity(domain.getCity());

        entity.setDepartments(MapperUtils.mapListWithParent(
                domain.getDepartments(),
                departmentMapper::toEntity,
                d -> d.setHospital(entity))
        );

        entity.setRooms(MapperUtils.mapListWithParent(
                domain.getRooms(),
                roomMapper::toEntity,
                r -> r.setHospital(entity))
        );

        return entity;
    }

    public Hospital toDomain(HospitalEntity entity) {
        if (entity == null) return null;
        Hospital domain = new Hospital();
        domain.setHospitalID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setHospitalName(entity.getHospitalName());
        domain.setCity(entity.getCity());

        if (entity.getDepartments() != null)
            domain.setDepartments(entity.getDepartments().stream()
                    .map(departmentMapper::toDomain)
                    .collect(Collectors.toList()));

        if (entity.getRooms() != null)
            domain.setRooms(entity.getRooms().stream()
                    .map(roomMapper::toDomain)
                    .collect(Collectors.toList()));

        return domain;
    }
}