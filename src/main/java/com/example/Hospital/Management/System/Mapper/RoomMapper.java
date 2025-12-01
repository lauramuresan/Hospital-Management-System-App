package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.Model.DBModel.RoomEntity;
import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;

public class RoomMapper {

    /**
     * Mapează de la modelul de domeniu (Room) la entitatea JPA (RoomEntity).
     * Asigură că ID-ul este NULL pentru o nouă înregistrare.
     */
    public static RoomEntity toEntity(Room domain) {
        if (domain == null) return null;

        RoomEntity entity = new RoomEntity();
        String idString = domain.getRoomID();

        // 1. CORECȚIE ID: Asigură că ID-ul este NULL pentru INSERT-uri
        if (idString != null && !idString.trim().isEmpty()) {
            try {
                entity.setId(MapperUtils.parseLong(idString));
            } catch (NumberFormatException e) {
                // În caz de eroare de format, tratăm ca entitate nouă (ID = null)
                entity.setId(null);
            }
        } else {
            entity.setId(null);
        }

        entity.setNumber(domain.getNumber());
        entity.setStatus(domain.getStatus());

        // 2. CORECȚIE CRUCIALĂ: Maparea capacity (Integer -> Integer)
        // Rezolvă eroarea de validare "Capacitatea este obligatorie."
        if (domain.getCapacity() != null) {
            entity.setCapacity(domain.getCapacity());
        } else {
            // Dacă Capacity e null în DTO, dar obligatoriu în DB, trebuie să-l lăsați null
            // Validarea @NotNull din RoomEntity va gestiona eroarea, dacă e necesar
            entity.setCapacity(null);
        }

        // 3. Mapează HospitalID la HospitalEntity (Proxy)
        entity.setHospital(MapperUtils.createEntityProxy(HospitalEntity.class, domain.getHospitalID()));

        return entity;
    }

    /**
     * Mapează de la entitatea JPA (RoomEntity) la modelul de domeniu (Room).
     */
    public static Room toDomain(RoomEntity entity) {
        if (entity == null) return null;

        Room domain = new Room();

        domain.setRoomID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setNumber(entity.getNumber());
        domain.setStatus(entity.getStatus());

        // 4. CORECȚIE CRUCIALĂ: Adaugă maparea capacity (din Integer înapoi în Integer)
        // Rezolvă eroarea de incompatibilitate de tip în Thymeleaf
        if (entity.getCapacity() != null) {
            domain.setCapacity(entity.getCapacity());
        }

        // 5. Mapează HospitalEntity la HospitalID
        if (entity.getHospital() != null && entity.getHospital().getId() != null)
            domain.setHospitalID(String.valueOf(entity.getHospital().getId()));

        return domain;
    }
}