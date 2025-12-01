package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.PatientMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBPatientRepository;
import org.springframework.stereotype.Component;

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

        // 1. VALIDARE BUSINESS: Unicitatea Email
        // Verificăm unicitatea doar dacă este o înregistrare nouă SAU dacă email-ul s-a schimbat la update.
        if (domain.getPatientID() == null || domain.getPatientID().isBlank() || !isExistingEmail(domain)) {
            if (jpaRepository.existsByPacientEmail(domain.getPacientEmail())) {
                // ✅ MESAJ NOU, GENERAL PENTRU UTILIZATOR
                throw new RuntimeException("Eroare la salvare: Pacientul există deja în baza de date cu aceste date esențiale (Email).");
            }
        }

        PatientEntity entity = PatientMapper.toEntity(domain);
        PatientEntity savedEntity = jpaRepository.save(entity);

        // Actualizăm ID-ul în domeniul modelului după salvare (pentru entitățile nou create)
        if (savedEntity.getId() != null) {
            domain.setPatientID(String.valueOf(savedEntity.getId()));
        }
    }

    /**
     * Verifică dacă email-ul existent aparține aceluiași Pacient la update.
     * @param domain Modelul de domeniu al Pacientului.
     * @return true dacă ID-ul este valid și email-ul nu s-a schimbat, false altfel.
     */
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

        // Asigură că ID-ul este un număr valid (pentru mesajul de eroare lizibil)
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