package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.RoomMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.RoomEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import com.example.Hospital.Management.System.SearchCriteria.RoomSearchCriteria;
import com.example.Hospital.Management.System.Repository.JPA.RoomSpecification;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBRoomRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomAdaptor implements AbstractRepository<Room> {

    private final DBRoomRepository jpaRepository;
    private final AbstractRepository<Hospital> hospitalRepository;
    private final RoomMapper mapper; // Injectat

    public RoomAdaptor(
            DBRoomRepository jpaRepository,
            @Qualifier("hospitalAdaptor") AbstractRepository<Hospital> hospitalRepository,
            RoomMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.hospitalRepository = hospitalRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Room domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Camera");

        validateHospitalExists(domain.getHospitalID());

        Long hospitalId = RepositoryValidationUtils.parseIdOrThrow(
                domain.getHospitalID(),
                "ID-ul Spitalului este obligatoriu si trebuie sa fie un numar valid."
        );

        if (domain.getRoomID() == null || domain.getRoomID().isBlank() || !isExistingRoomNumber(domain)) {
            if (jpaRepository.existsByNumberAndHospitalId(domain.getNumber(), hospitalId)) {
                throw new RuntimeException("Numarul camerei '" + domain.getNumber() +
                        "' este deja folosit in acest spital.");
            }
        }
        jpaRepository.save(mapper.toEntity(domain)); // Folosim instanța mapper
    }

    private void validateHospitalExists(String hospitalId) {
        if (hospitalId != null) {
            Hospital hospital = hospitalRepository.findById(hospitalId);
            if (hospital == null) {
                throw new RuntimeException("Spitalul cu ID-ul '" + hospitalId + "' nu exista.");
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
        } catch (NumberFormatException e) { return false; }
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
        return jpaRepository.findById(parsed).map(mapper::toDomain).orElse(null);
    }

    @Override
    public List<Room> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Room> findAll(Sort sort) {
        return findAll(null, sort);
    }

    @Override
    public List<Room> findAll(Object searchCriteria, Sort sort) {
        Specification<RoomEntity> spec = null;

        if (searchCriteria instanceof RoomSearchCriteria) {
            spec = RoomSpecification.filterByCriteria((RoomSearchCriteria) searchCriteria);
        }

        return jpaRepository.findAll(spec, sort).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}