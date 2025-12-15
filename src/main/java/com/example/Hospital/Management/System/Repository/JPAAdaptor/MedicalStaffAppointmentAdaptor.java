package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.MedicalStaffAppointmentMapper;
import com.example.Hospital.Management.System.Model.DBModel.MedicalStaffAppointmentEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaffAppointment;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBMedicalStaffAppointmentRepository;
import org.springframework.data.domain.Sort; // IMPORT NOU
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicalStaffAppointmentAdaptor implements AbstractRepository<MedicalStaffAppointment> {

    private final DBMedicalStaffAppointmentRepository jpaRepository;

    public MedicalStaffAppointmentAdaptor(DBMedicalStaffAppointmentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(MedicalStaffAppointment domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Alocarea Personalului Medical");

        // 1. Verifică ID-ul alocării pentru a determina INSERT sau UPDATE
        String domainIdString = domain.getMedicalStaffAppointmentID();
        Long existingId = RepositoryValidationUtils.parseIdOrNull(domainIdString);

        // 2. Extrage și validează ID-urile relațiilor
        String appointmentIdString = domain.getAppointmentID();
        String medicalStaffIdString = domain.getMedicalStaffID();

        Long appointmentIdLong = RepositoryValidationUtils.parseIdOrThrow(appointmentIdString, "ID-ul Programării este invalid sau lipsește.");
        Long medicalStaffIdLong = RepositoryValidationUtils.parseIdOrThrow(medicalStaffIdString, "ID-ul Personalului Medical este invalid sau lipsește.");

        // 3. VALIDARE BUSINESS: Unicitate Programare + Personal
        // Aplicăm validarea doar dacă este o intrare nouă (INSERT)
        if (existingId == null) {
            // Verificarea de unicitate: Programare + Personal Medical
            // Notă: Logica de unicitate din Adaptor ar trebui să fie mai robustă (verificând doctor_id ȘI nurse_id).
            // Aici ne bazăm pe presupunerea că Mapper-ul a setat doctor_id conform logicii stabilite anterior.
            if (jpaRepository.existsByAppointmentIdAndDoctorId(appointmentIdLong, medicalStaffIdLong)) {
                throw new RuntimeException("Eroare: Personalul medical cu ID-ul " + medicalStaffIdString + " este deja alocat programării " + appointmentIdString + ".");
            }
        }

        // 4. MAPARE ȘI SALVARE
        MedicalStaffAppointmentEntity entity;

        if (existingId != null) {
            entity = jpaRepository.findById(existingId)
                    .orElseThrow(() -> new RuntimeException("Alocarea cu ID-ul " + existingId + " nu a fost găsită pentru actualizare."));

            MedicalStaffAppointmentMapper.mapDomainToEntity(domain, entity);
        } else {
            entity = MedicalStaffAppointmentMapper.mapDomainToEntity(domain, null);
        }

        jpaRepository.save(entity);
    }

    @Override
    public void delete(MedicalStaffAppointment domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Alocarea Personalului Medical");
        RepositoryValidationUtils.requireIdForDelete(domain.getMedicalStaffAppointmentID(), "ID-ul alocării");

        Long id = RepositoryValidationUtils.parseIdOrThrow(domain.getMedicalStaffAppointmentID(), "ID-ul alocării este invalid.");
        jpaRepository.deleteById(id);
    }

    @Override
    public MedicalStaffAppointment findById(String id) {
        Long parsed = RepositoryValidationUtils.parseIdOrNull(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed).map(MedicalStaffAppointmentMapper::toDomain).orElse(null);
    }

    @Override
    public List<MedicalStaffAppointment> findAll() {
        return jpaRepository.findAll().stream().map(MedicalStaffAppointmentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<MedicalStaffAppointment> findAll(Sort sort) {
        if (sort == null) {
            return findAll();
        }
        return jpaRepository.findAll(sort).stream()
                .map(MedicalStaffAppointmentMapper::toDomain)
                .collect(Collectors.toList());
    }
}