package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.PatientMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBPatientRepository;
import com.example.Hospital.Management.System.Repository.JPA.PatientSpecification;
import com.example.Hospital.Management.System.SearchCriteria.PatientSearchCriteria;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientAdaptor implements AbstractRepository<Patient> {

    private final DBPatientRepository jpaRepository;
    private final PatientMapper mapper; // Variabila injectată

    // Constructor pentru injecție
    public PatientAdaptor(DBPatientRepository jpaRepository, PatientMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Patient domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Pacientul");

        // Validare Data Naștere
        if (domain.getPatientBirthDate() != null && domain.getPatientBirthDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Eroare la salvare: Data de nastere (" + domain.getPatientBirthDate() + ") nu poate fi in viitor.");
        }

        // Validare Unicitate Email
        if (domain.getPatientID() == null || domain.getPatientID().isBlank() || !isExistingEmail(domain)) {
            if (jpaRepository.existsByPacientEmail(domain.getPacientEmail())) {
                throw new RuntimeException("Eroare la salvare: Pacientul exista deja in baza de date cu aceste date esentiale (Email).");
            }
        }

        // --- CORECȚIA ESTE AICI ---
        // Folosim 'mapper.toEntity', NU 'PatientMapper.toEntity'
        PatientEntity entity = mapper.toEntity(domain);

        PatientEntity savedEntity = jpaRepository.save(entity);

        if (savedEntity.getId() != null) {
            domain.setPatientID(String.valueOf(savedEntity.getId()));
        }
    }

    private boolean isExistingEmail(Patient domain) {
        if (domain.getPatientID() == null || domain.getPatientID().isBlank()) return false;
        try {
            Long id = MapperUtils.parseLong(domain.getPatientID());
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
        RepositoryValidationUtils.requireIdForDelete(domain.getPatientID(), "ID-ul pacientului");
        Long id = RepositoryValidationUtils.parseIdOrThrow(domain.getPatientID(), "ID-ul pacientului este invalid sau lipseste pentru stergere.");
        jpaRepository.deleteById(id);
    }

    @Override
    public Patient findById(String id) {
        Long parsed = RepositoryValidationUtils.parseIdOrNull(id);
        if (parsed == null) return null;

        // Folosim 'mapper::toDomain', NU 'PatientMapper::toDomain'
        return jpaRepository.findById(parsed).map(mapper::toDomain).orElse(null);
    }

    @Override
    public List<Patient> findAll() {
        // Folosim 'mapper::toDomain'
        return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Patient> findAll(Sort sort) {
        return findAll(null, sort);
    }

    @Override
    public List<Patient> findAll(Object searchCriteria, Sort sort) {
        Specification<PatientEntity> spec = null;

        if (searchCriteria instanceof PatientSearchCriteria) {
            spec = PatientSpecification.filterByCriteria((PatientSearchCriteria) searchCriteria);
        }

        if (sort == null) {
            // Folosim 'mapper::toDomain'
            return jpaRepository.findAll(spec).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        }

        // Folosim 'mapper::toDomain'
        return jpaRepository.findAll(spec, sort).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}