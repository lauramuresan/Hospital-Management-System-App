package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.RoomMapper;
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
    // Eliminat: private final RoomMapper mapper;

    // CORECȚIE: Eliminăm parametrul RoomMapper din constructor
    public RoomAdaptor(DBRoomRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
        // Eliminat: this.mapper = mapper;
    }

    @Override
    public void save(Room domain) {
//        if (domain.getRoomID() == null || !isExistingRoomNumber(domain)) {
//            if (jpaRepository.existsByNumber(domain.getNumber())) {
//                throw new RuntimeException("Numărul camerei " + domain.getNumber() + " este deja folosit.");
//            }
//        }
        // CORECȚIE: Apelăm metoda statică direct pe clasa RoomMapper
        jpaRepository.save(RoomMapper.toEntity(domain));
    }

    private boolean isExistingRoomNumber(Room domain) {
        if (domain.getRoomID() == null) return false;
        try {
            Long id = Long.valueOf(domain.getRoomID());
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
            jpaRepository.deleteById(Long.valueOf(domain.getRoomID()));
        }
    }

    @Override
    public Room findById(String id) {
        try {
            // CORECȚIE: Folosim referința pe CLASĂ (RoomMapper::toDomain)
            return jpaRepository.findById(Long.valueOf(id)).map(RoomMapper::toDomain).orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Room> findAll() {
        // CORECȚIE: Folosim referința pe CLASĂ (RoomMapper::toDomain)
        return jpaRepository.findAll().stream().map(RoomMapper::toDomain).collect(Collectors.toList());
    }
}