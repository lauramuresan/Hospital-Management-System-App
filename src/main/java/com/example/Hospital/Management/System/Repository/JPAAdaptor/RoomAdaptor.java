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
    private final RoomMapper mapper;

    public RoomAdaptor(DBRoomRepository jpaRepository, RoomMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Room domain) {
//        if (domain.getRoomID() == null || !isExistingRoomNumber(domain)) {
//            if (jpaRepository.existsByNumber(domain.getNumber())) {
//                throw new RuntimeException("Numărul camerei " + domain.getNumber() + " este deja folosit.");
//            }
//        }
        jpaRepository.save(mapper.toEntity(domain));
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
            return jpaRepository.findById(Long.valueOf(id)).map(mapper::toDomain).orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Room> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}