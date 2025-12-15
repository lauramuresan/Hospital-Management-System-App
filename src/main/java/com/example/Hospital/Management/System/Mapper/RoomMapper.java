package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Model.DBModel.RoomEntity;
import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class RoomMapper {


    public RoomEntity toEntity(Room domain) {
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
        if (domain.getCapacity() != null) entity.setCapacity(domain.getCapacity());
        entity.setHospital(MapperUtils.createEntityProxy(HospitalEntity.class, domain.getHospitalID()));
        return entity;
    }


    public Room toDomain(RoomEntity entity) {
        if (entity == null) return null;
        Room domain = new Room();
        domain.setRoomID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setNumber(entity.getNumber());
        domain.setStatus(entity.getStatus());
        if (entity.getCapacity() != null) domain.setCapacity(entity.getCapacity());
        if (entity.getHospital() != null && entity.getHospital().getId() != null)
            domain.setHospitalID(String.valueOf(entity.getHospital().getId()));

        if (entity.getAppointments() != null) {
            domain.setAppointments(entity.getAppointments().stream()
                    .map(appEntity -> {
                        Appointment app = new Appointment();
                        app.setAppointmentID(String.valueOf(appEntity.getId()));
                        return app;
                    })
                    .collect(Collectors.toList()));
        } else {
            domain.setAppointments(Collections.emptyList());
        }

        return domain;
    }
}