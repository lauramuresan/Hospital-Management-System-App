package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.PatientMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBPatientRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientAdaptor implements AbstractRepository<Patient> {

    private final DBPatientRepository jpaRepository;


    public PatientAdaptor(DBPatientRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Patient domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Pacientul");

        // 1. VALIDARE BUSINESS: Data de Naștere în Viitor
        if (domain.getPatientBirthDate() != null && domain.getPatientBirthDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Eroare la salvare: Data de naștere (" + domain.getPatientBirthDate() + ") nu poate fi în viitor.");
        }

        // 2. VALIDARE BUSINESS: Unicitatea Email
        // Se verifică unicitatea doar dacă:
        // a) este o salvare nouă (ID-ul lipsește)
        // SAU
        // b) este un update, dar pacientul existent NU are deja acest email
        if (domain.getPatientID() == null || domain.getPatientID().isBlank() || !isExistingEmail(domain)) {
            if (jpaRepository.existsByPacientEmail(domain.getPacientEmail())) {
                throw new RuntimeException("Eroare la salvare: Pacientul există deja în baza de date cu aceste date esențiale (Email).");
            }
        }

        PatientEntity entity = PatientMapper.toEntity(domain);
        PatientEntity savedEntity = jpaRepository.save(entity);

        // Actualizează ID-ul modelului Domain cu cel generat de DB
        if (savedEntity.getId() != null) {
            domain.setPatientID(String.valueOf(savedEntity.getId()));
        }
    }

    // Metoda isExistingEmail() este păstrată
    private boolean isExistingEmail(Patient domain) {
        if (domain.getPatientID() == null || domain.getPatientID().isBlank()) return false;
        try {
            Long id = MapperUtils.parseLong(domain.getPatientID());

            // Asigurăm că ID-ul este valid înainte de a interoga DB
            if (id == null) return false;

            return jpaRepository.findById(id)
                    .map(PatientEntity::getPacientEmail)
                    .filter(email -> email.equals(domain.getPacientEmail()))
                    .isPresent();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void delete(Patient domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Pacientul");

        // Asigură că ID-ul nu lipsește
        RepositoryValidationUtils.requireIdForDelete(domain.getPatientID(), "ID-ul pacientului");

        // Asigură că ID-ul este un număr valid
        Long id = RepositoryValidationUtils.parseIdOrThrow(domain.getPatientID(), "ID-ul pacientului este invalid sau lipsește pentru ștergere.");
        jpaRepository.deleteById(id);
    }

    @Override
    public Patient findById(String id) {
        Long parsed = RepositoryValidationUtils.parseIdOrNull(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed).map(PatientMapper::toDomain).orElse(null);
    }

    @Override
    public List<Patient> findAll() {
        return jpaRepository.findAll().stream().map(PatientMapper::toDomain).collect(Collectors.toList());
    }
}