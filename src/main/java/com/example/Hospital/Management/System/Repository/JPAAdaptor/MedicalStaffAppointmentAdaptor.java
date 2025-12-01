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
        // Preluăm ID-urile ca String-uri inițial
        String appointmentIdString = domain.getAppointmentID();
        String medicalStaffIdString = domain.getMedicalStaffID();

        // Validăm că sunt numere valide (și obținem Long-ul pentru Repository)
        Long appointmentIdLong = RepositoryValidationUtils.parseIdOrThrow(appointmentIdString, "ID-ul Programării este invalid sau lipsește.");
        Long medicalStaffIdLong = RepositoryValidationUtils.parseIdOrThrow(medicalStaffIdString, "ID-ul Personalului Medical este invalid sau lipsește.");

        // 2. Extragem rolul pentru validarea de unicitate
        String staffIdString = medicalStaffIdString;

        // 3. VALIDARE BUSINESS: Unicitate Programare + Personal
        if (domain.getMedicalStaffAppointmentID() == null || domain.getMedicalStaffAppointmentID().isBlank()) {

            // Tentativă de a parsa ID-ul ca Doctor (Folosim ID-ul Long validat)
            Long doctorId = medicalStaffIdLong; // Folosim medicalStaffIdLong
            Long nurseId = null; // Presupunem că doar unul este setat

            // Dacă unicitatea nu este respectată (caz Doctor)
            // Notă: Metoda Repository așteaptă Long-ul, dar validarea din Adaptor folosește Long-ul
            if (jpaRepository.existsByAppointmentIdAndDoctorId(appointmentIdLong, doctorId)) {
                throw new RuntimeException("Eroare: Personalul medical cu ID-ul " + medicalStaffIdString + " este deja alocat programării " + appointmentIdString + ".");
            }

            // Dacă ai logică de Nurse, o poți adăuga aici folosind nurseId (Long)
        }

        // 4. Salvarea
        // Argumente necesare pentru createEntityFromIds (Toate trebuie să fie String)
        String doctorIdToMap = medicalStaffIdString;
        String nurseIdToMap = null;
        String appointmentIdToMap = appointmentIdString; // Folosim versiunea String originală

        // ✅ CORECȚIE: Toate argumentele sunt acum String, rezolvând eroarea de tip.
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