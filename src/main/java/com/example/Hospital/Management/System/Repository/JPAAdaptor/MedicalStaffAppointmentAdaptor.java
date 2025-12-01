package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.MedicalStaffAppointmentMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaffAppointment;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBMedicalStaffAppointmentRepository;
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

        // 1. Validare FK (ID Programare și ID Personal Medical)
        String appointmentIdString = domain.getAppointmentID();
        String medicalStaffIdString = domain.getMedicalStaffID();

        Long appointmentIdLong = RepositoryValidationUtils.parseIdOrThrow(appointmentIdString, "ID-ul Programării este invalid sau lipsește.");
        Long medicalStaffIdLong = RepositoryValidationUtils.parseIdOrThrow(medicalStaffIdString, "ID-ul Personalului Medical este invalid sau lipsește.");

        // 2. Extragem rolul pentru validarea de unicitate
        String staffIdString = medicalStaffIdString;

        // 3. VALIDARE BUSINESS: Unicitate Programare + Personal
        if (domain.getMedicalStaffAppointmentID() == null || domain.getMedicalStaffAppointmentID().isBlank()) {

            Long doctorId = medicalStaffIdLong;
            Long nurseId = null;

            if (jpaRepository.existsByAppointmentIdAndDoctorId(appointmentIdLong, doctorId)) {
                throw new RuntimeException("Eroare: Personalul medical cu ID-ul " + medicalStaffIdString + " este deja alocat programării " + appointmentIdString + ".");
            }
        }

        String doctorIdToMap = medicalStaffIdString;
        String nurseIdToMap = null;
        String appointmentIdToMap = appointmentIdString;
        jpaRepository.save(MedicalStaffAppointmentMapper.createEntityFromIds(appointmentIdToMap, doctorIdToMap, nurseIdToMap));
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
}