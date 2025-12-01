package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.Model.DBModel.RoomEntity;
import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;

public class RoomMapper {

    /**
     * Mapează de la modelul de domeniu (Room) la entitatea JPA (RoomEntity).
     */
    public static RoomEntity toEntity(Room domain) {
        if (domain == null) return null;

        RoomEntity entity = new RoomEntity();
        String idString = domain.getRoomID();
        if (idString != null && !idString.trim().isEmpty()) {
            try {
                entity.setId(MapperUtils.parseLong(idString));
            } catch (NumberFormatException e) {
                entity.setId(null);
            }
        } else {
            entity.setId(null);
        }

        entity.setNumber(domain.getNumber());
        entity.setStatus(domain.getStatus());
        if (domain.getCapacity() != null) {
            entity.setCapacity(domain.getCapacity());
        } else {
            entity.setCapacity(null);
        }
        entity.setHospital(MapperUtils.createEntityProxy(HospitalEntity.class, domain.getHospitalID()));

        return entity;
    }

    public static Room toDomain(RoomEntity entity) {
        if (entity == null) return null;

        Room domain = new Room();

        domain.setRoomID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setNumber(entity.getNumber());
        domain.setStatus(entity.getStatus());

        if (entity.getCapacity() != null) {
            domain.setCapacity(entity.getCapacity());
        }

        if (entity.getHospital() != null && entity.getHospital().getId() != null)
            domain.setHospitalID(String.valueOf(entity.getHospital().getId()));

        return domain;
    }
}