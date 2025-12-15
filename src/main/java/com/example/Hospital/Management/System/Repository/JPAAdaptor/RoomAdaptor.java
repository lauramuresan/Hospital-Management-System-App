package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.RoomMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.RoomEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBRoomRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort; // IMPORT NOU
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomAdaptor implements AbstractRepository<Room> {

    private final DBRoomRepository jpaRepository;
    private final AbstractRepository<Hospital> hospitalRepository;

    public RoomAdaptor(
            DBRoomRepository jpaRepository,
            @Qualifier("hospitalAdaptor") AbstractRepository<Hospital> hospitalRepository
    ) {
        this.jpaRepository = jpaRepository;
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public void save(Room domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Camera");

        // 1. Validare FK (Hospital ID)
        validateHospitalExists(domain.getHospitalID());

        Long hospitalId = RepositoryValidationUtils.parseIdOrThrow(
                domain.getHospitalID(),
                "ID-ul Spitalului este obligatoriu si trebuie sa fie un numar valid."
        );

        // 2. Unicizare Numar Camera PER SPITAL
        if (domain.getRoomID() == null || domain.getRoomID().isBlank() || !isExistingRoomNumber(domain)) {

            if (jpaRepository.existsByNumberAndHospitalId(domain.getNumber(), hospitalId)) {
                throw new RuntimeException("Numarul camerei '" + domain.getNumber() +
                        "' este deja folosit in Spitalul cu ID-ul " + domain.getHospitalID() +
                        ". Fiecare camera trebuie sa aiba un numar unic per spital.");
            }
        }

        jpaRepository.save(RoomMapper.toEntity(domain));
    }

    private void validateHospitalExists(String hospitalId) {
        if (hospitalId != null) {
            Hospital hospital = hospitalRepository.findById(hospitalId);

            if (hospital == null) {
                throw new RuntimeException("Spitalul cu ID-ul '" + hospitalId +
                        "' nu exista. Va rugam sa introduceti un ID de spital valid.");
            }
        }
    }


    private boolean isExistingRoomNumber(Room domain) {
        if (domain.getRoomID() == null || domain.getRoomID().isBlank()) return false;

        try {
            Long id = MapperUtils.parseLong(domain.getRoomID());
            Long hospitalId = MapperUtils.parseLong(domain.getHospitalID());

            if (id == null || hospitalId == null) return false;

            return jpaRepository.findById(id)
                    .filter(entity ->
                            entity.getNumber().equals(domain.getNumber()) &&
                                    entity.getHospital().getId().equals(hospitalId))
                    .isPresent();

        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void delete(Room domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Camera");
        RepositoryValidationUtils.requireIdForDelete(domain.getRoomID(), "ID-ul camerei");

        Long id = RepositoryValidationUtils.parseIdOrThrow(domain.getRoomID(), "ID-ul camerei este invalid.");
        jpaRepository.deleteById(id);
    }

    @Override
    public Room findById(String id) {
        Long parsed = RepositoryValidationUtils.parseIdOrNull(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed)
                .map(RoomMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Room> findAll() {
        return jpaRepository.findAll().stream()
                .map(RoomMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Room> findAll(Sort sort) {
        if (sort == null) {
            return findAll();
        }
        return jpaRepository.findAll(sort).stream()
                .map(RoomMapper::toDomain)
                .collect(Collectors.toList());
    }
}