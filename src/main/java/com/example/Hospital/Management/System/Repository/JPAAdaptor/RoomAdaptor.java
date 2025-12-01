package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.RoomMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.RoomEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBRoomRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomAdaptor implements AbstractRepository<Room> {

    private final DBRoomRepository jpaRepository;

    public RoomAdaptor(DBRoomRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Room domain) {
        // 1. Validare Unicizare Număr Cameră
        // 🛑 COMENTAT TEMPORAR PENTRU IZOLAREA ERORII "Invalid ID"
        /*
        if (domain.getRoomID() == null || !isExistingRoomNumber(domain)) {
            if (jpaRepository.existsByNumber(domain.getNumber())) {
                throw new RuntimeException("Numărul camerei " + domain.getNumber() + " este deja folosit.");
            }
        }
        */

        // 2. Salvare
        jpaRepository.save(RoomMapper.toEntity(domain));
    }

    /**
     * Verifică dacă numărul camerei din modelul de domeniu se potrivește cu numărul
     * camerei existent în baza de date pentru același ID.
     */
    private boolean isExistingRoomNumber(Room domain) {
        if (domain.getRoomID() == null) return false;
        try {
            Long id = MapperUtils.parseLong(domain.getRoomID());
            return jpaRepository.findById(id)
                    .map(RoomEntity::getNumber)
                    .filter(number -> number.equals(domain.getNumber()))
                    .isPresent();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void delete(Room domain) {
        if (domain.getRoomID() != null) {
            try {
                jpaRepository.deleteById(MapperUtils.parseLong(domain.getRoomID()));
            } catch (NumberFormatException e) {
                // Ignore delete if ID is invalid
            }
        }
    }

    @Override
    public Room findById(String id) {
        try {
            return jpaRepository.findById(MapperUtils.parseLong(id))
                    .map(RoomMapper::toDomain)
                    .orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Room> findAll() {
        return jpaRepository.findAll().stream()
                .map(RoomMapper::toDomain)
                .collect(Collectors.toList());
    }
}