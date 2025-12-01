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
        RepositoryValidationUtils.requireDomainNonNull(domain, "Camera");

        // 1. Validare FK (Hospital ID)
        Long hospitalId = RepositoryValidationUtils.parseIdOrThrow(
                domain.getHospitalID(),
                "ID-ul Spitalului este obligatoriu și trebuie să fie un număr valid."
        );
        // (Verificarea existenței spitalului ar trebui adăugată aici, dacă este necesar)

        // 2. VALIDARE BUSINESS: Unicizare Număr Cameră PER SPITAL
        if (domain.getRoomID() == null || domain.getRoomID().isBlank() || !isExistingRoomNumber(domain)) {

            // ✅ CORECȚIE LOGICĂ: Verifică unicitatea numărului camerei doar în spitalul specificat
            if (jpaRepository.existsByNumberAndHospitalId(domain.getNumber(), hospitalId)) {
                // ✅ MESAJ LIZIBIL PENTRU UTILIZATOR
                throw new RuntimeException("Numărul camerei '" + domain.getNumber() +
                        "' este deja folosit în Spitalul cu ID-ul " + domain.getHospitalID() +
                        ". Fiecare cameră trebuie să aibă un număr unic per spital.");
            }
        }

        jpaRepository.save(RoomMapper.toEntity(domain));
    }

    /**
     * Verifică dacă numărul camerei și ID-ul spitalului NU s-au schimbat la UPDATE.
     * Această metodă este necesară pentru a permite editarea altor câmpuri fără a arunca excepții de unicitate.
     */
    private boolean isExistingRoomNumber(Room domain) {
        if (domain.getRoomID() == null || domain.getRoomID().isBlank()) return false;

        try {
            Long id = MapperUtils.parseLong(domain.getRoomID());
            Long hospitalId = MapperUtils.parseLong(domain.getHospitalID()); // Extragem și ID-ul spitalului curent

            if (id == null || hospitalId == null) return false;

            // Verificăm dacă entitatea existentă are același Număr ȘI același Hospital ID
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
}