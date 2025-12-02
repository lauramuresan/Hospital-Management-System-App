package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.RoomMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.RoomEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Room;
import com.example.Hospital.Management.System.Model.GeneralModel.Hospital; // << NOU
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBRoomRepository;
import org.springframework.beans.factory.annotation.Qualifier; // << NECESAR PENTRU INJECȚIA CORECTĂ
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomAdaptor implements AbstractRepository<Room> {

    private final DBRoomRepository jpaRepository;
    // 💡 Injectăm repository-ul pentru Spital pentru validarea FK
    private final AbstractRepository<Hospital> hospitalRepository; // << NOU

    public RoomAdaptor(
            DBRoomRepository jpaRepository,
            @Qualifier("hospitalAdaptor") AbstractRepository<Hospital> hospitalRepository // << Constructor Modificat
    ) {
        this.jpaRepository = jpaRepository;
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public void save(Room domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Camera");

        // 1. Validare FK (Hospital ID) - NOU: Verifică existența Spitalului
        validateHospitalExists(domain.getHospitalID());

        // Parsează ID-ul pentru a fi folosit în repository-ul JPA (ex: existsByNumberAndHospitalId)
        Long hospitalId = RepositoryValidationUtils.parseIdOrThrow(
                domain.getHospitalID(),
                "ID-ul Spitalului este obligatoriu și trebuie să fie un număr valid."
        );

        // 2. Unicizare Număr Cameră PER SPITAL
        if (domain.getRoomID() == null || domain.getRoomID().isBlank() || !isExistingRoomNumber(domain)) {

            // Verifică unicitatea numărului camerei doar în spitalul specificat
            if (jpaRepository.existsByNumberAndHospitalId(domain.getNumber(), hospitalId)) {
                throw new RuntimeException("Numărul camerei '" + domain.getNumber() +
                        "' este deja folosit în Spitalul cu ID-ul " + domain.getHospitalID() +
                        ". Fiecare cameră trebuie să aibă un număr unic per spital.");
            }
        }

        jpaRepository.save(RoomMapper.toEntity(domain));
    }

    /**
     * Verifică dacă ID-ul de Spital furnizat există. Aruncă o excepție
     * lizibilă dacă spitalul nu este găsit.
     */
    private void validateHospitalExists(String hospitalId) {
        if (hospitalId != null) {
            // Caută spitalul după ID folosind repository-ul injectat
            Hospital hospital = hospitalRepository.findById(hospitalId);

            // Dacă spitalul nu este găsit, aruncă o eroare lizibilă
            if (hospital == null) {
                throw new RuntimeException("Spitalul cu ID-ul '" + hospitalId +
                        "' nu există. Vă rugăm să introduceți un ID de spital valid.");
            }
        }
    }


    private boolean isExistingRoomNumber(Room domain) {
        if (domain.getRoomID() == null || domain.getRoomID().isBlank()) return false;

        try {
            Long id = MapperUtils.parseLong(domain.getRoomID());
            Long hospitalId = MapperUtils.parseLong(domain.getHospitalID());

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