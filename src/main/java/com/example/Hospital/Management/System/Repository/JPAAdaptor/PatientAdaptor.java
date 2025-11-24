package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.PatientMapper;
import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBPatientRepository; // Repository-ul Spring Data JPA
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientAdaptor implements AbstractRepository<Patient> {

    private final DBPatientRepository jpaRepository;
    private final PatientMapper mapper;

    // Constructorul primește JpaRepository și Mapper
    public PatientAdaptor(DBPatientRepository jpaRepository, PatientMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Patient domain) {
        // Business Validation: Unicitatea Email-ului
        if (domain.getPatientID() == null || !isExistingEmail(domain)) {
            if (jpaRepository.existsByPacientEmail(domain.getPacientEmail())) {
                throw new RuntimeException("Emailul " + domain.getPacientEmail() + " este deja înregistrat.");
            }
        }

        PatientEntity entity = mapper.toEntity(domain);
        PatientEntity savedEntity = jpaRepository.save(entity);
        domain.setPatientID(String.valueOf(savedEntity.getId()));
    }

    // Metodă ajutătoare pentru a evita excepția la update dacă emailul nu s-a schimbat
    private boolean isExistingEmail(Patient domain) {
        if (domain.getPatientID() == null) return false;
        try {
            Long id = Long.valueOf(domain.getPatientID());
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
        if (domain.getPatientID() != null) {
            jpaRepository.deleteById(Long.valueOf(domain.getPatientID()));
        }
    }

    @Override
    public Patient findById(String id) {
        try {
            return jpaRepository.findById(Long.valueOf(id)).map(mapper::toDomain).orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Patient> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}