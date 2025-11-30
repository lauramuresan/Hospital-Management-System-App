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
        // Business Validation: Unicitatea PacientEmail
        // Verifică unicitatea DOAR dacă este o înregistrare nouă (ID null)
        // sau dacă se face update și s-a schimbat email-ul.
        if (domain.getPatientID() == null || !isExistingEmail(domain)) {
            if (jpaRepository.existsByPacientEmail(domain.getPacientEmail())) {
                throw new RuntimeException("Emailul " + domain.getPacientEmail() + " este deja înregistrat.");
            }
        }

        PatientEntity entity = PatientMapper.toEntity(domain);
        PatientEntity savedEntity = jpaRepository.save(entity);
        // Actualizăm ID-ul în domeniul modelului după salvare (pentru înregistrare nouă)
        if (savedEntity.getId() != null) {
            domain.setPatientID(String.valueOf(savedEntity.getId()));
        }
    }

    /**
     * Verifică dacă email-ul existent aparține aceluiași Pacient la update.
     */
    private boolean isExistingEmail(Patient domain) {
        if (domain.getPatientID() == null) return false;
        try {
            Long id = MapperUtils.parseLong(domain.getPatientID());
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
            jpaRepository.deleteById(MapperUtils.parseLong(domain.getPatientID()));
        }
    }

    @Override
    public Patient findById(String id) {
        try {
            return jpaRepository.findById(MapperUtils.parseLong(id)).map(PatientMapper::toDomain).orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Patient> findAll() {
        return jpaRepository.findAll().stream().map(PatientMapper::toDomain).collect(Collectors.toList());
    }
}