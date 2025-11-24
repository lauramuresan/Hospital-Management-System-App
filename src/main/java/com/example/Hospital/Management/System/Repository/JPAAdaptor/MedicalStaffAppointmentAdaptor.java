package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.MedicalStaffAppointmentMapper;
import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaffAppointment;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBMedicalStaffAppointmentRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicalStaffAppointmentAdaptor implements AbstractRepository<MedicalStaffAppointment> {

    private final DBMedicalStaffAppointmentRepository jpaRepository;
    private final MedicalStaffAppointmentMapper mapper;

    public MedicalStaffAppointmentAdaptor(DBMedicalStaffAppointmentRepository jpaRepository, MedicalStaffAppointmentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(MedicalStaffAppointment domain) {
        // Business Validation: Verifică dacă AppointmentID, DoctorID și/sau NurseID există.
        // NOTĂ: Dacă modelul de domeniu MedicalStaffAppointment este doar un holder de ID-uri,
        // salvarea va fi complexă și necesită toți Repositories (Doctor, Nurse, Appointment) injectați aici.
        // În acest context, mapper.createEntityFromIds(domain.getAppointmentId(), domain.getDoctorId(), domain.getNurseId());
        // ar fi metoda folosită.

        // Presupunând că Mapper-ul poate crea entitatea de legătură:
        jpaRepository.save(mapper.createEntityFromIds(domain.getAppointmentID(), domain.getMedicalStaffID(), domain.getMedicalStaffID()));
    }

    @Override
    public void delete(MedicalStaffAppointment domain) {
        jpaRepository.deleteById(Long.valueOf(domain.getMedicalStaffAppointmentID()));
    }

    @Override
    public MedicalStaffAppointment findById(String id) { return null; }
    @Override
    public List<MedicalStaffAppointment> findAll() { return List.of(); }
}