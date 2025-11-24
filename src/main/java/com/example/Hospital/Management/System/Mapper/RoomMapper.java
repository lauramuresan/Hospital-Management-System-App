package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.Model.DBModel.RoomEntity;
import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;

public class RoomMapper {
    public static RoomEntity toEntity(Room domain) {
        if (domain == null) return null;
        RoomEntity entity = new RoomEntity();

        if (domain.getRoomID() != null) {
            try { entity.setId(Long.valueOf(domain.getRoomID())); } catch (NumberFormatException e) {}
        }
        entity.setNumber(domain.getNumber());
        entity.setCapacity((int) domain.getCapacity());
        entity.setStatus(domain.getStatus());

        // Maparea Hospital (Proxy)
        if (domain.getHospitalID() != null) {
            HospitalEntity hospitalProxy = new HospitalEntity();
            try { hospitalProxy.setId(Long.valueOf(domain.getHospitalID())); } catch (NumberFormatException e) { return null; }
            entity.setHospital(hospitalProxy);
        }

        return entity;
    }

    public static Room toDomain(RoomEntity entity) {
        if (entity == null) return null;
        Room domain = new Room();

        domain.setRoomID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setNumber(entity.getNumber());
        domain.setCapacity(entity.getCapacity());
        domain.setStatus(entity.getStatus());

        if (entity.getHospital() != null && entity.getHospital().getId() != null) {
            // Presupunând că POJO-ul Room are un setHospitalID(String)
            domain.setHospitalID(String.valueOf(entity.getHospital().getId()));
        }

        return domain;
    }
}