package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.PatientMapper;
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
    // Eliminat: private final PatientMapper mapper;

    // CORECȚIE: Eliminăm parametrul PatientMapper din constructor
    public PatientAdaptor(DBPatientRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
        // Eliminat: this.mapper = mapper;
    }

    @Override
    public void save(Patient domain) {
        // Business Validation: Unicitatea Email-ului
        if (domain.getPatientID() == null || !isExistingEmail(domain)) {
            // Presupunând că existsByPacientEmail este definită în DBPatientRepository
            if (jpaRepository.existsByPacientEmail(domain.getPacientEmail())) {
                throw new RuntimeException("Emailul " + domain.getPacientEmail() + " este deja înregistrat.");
            }
        }

        // CORECȚIE: Apelăm metoda statică direct pe clasa PatientMapper
        PatientEntity entity = PatientMapper.toEntity(domain);
        PatientEntity savedEntity = jpaRepository.save(entity);

        // Returnarea ID-ului generat către POJO-ul de domeniu este o practică bună:
        if (savedEntity.getId() != null) {
            domain.setPatientID(String.valueOf(savedEntity.getId()));
        }
    }

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
            // CORECȚIE: Folosim referința pe CLASĂ (PatientMapper::toDomain)
            return jpaRepository.findById(Long.valueOf(id)).map(PatientMapper::toDomain).orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Patient> findAll() {
        // CORECȚIE: Folosim referința pe CLASĂ (PatientMapper::toDomain)
        return jpaRepository.findAll().stream().map(PatientMapper::toDomain).collect(Collectors.toList());
    }
}