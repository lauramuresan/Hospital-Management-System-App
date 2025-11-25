package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.MedicalStaffAppointmentMapper;
import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaffAppointment;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBMedicalStaffAppointmentRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBDoctorRepository; // NOU
import com.example.Hospital.Management.System.Repository.DBRepository.DBNurseRepository;  // NOU
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class MedicalStaffAppointmentAdaptor implements AbstractRepository<MedicalStaffAppointment> {

    private final DBMedicalStaffAppointmentRepository jpaRepository;
    private final DBDoctorRepository doctorJpaRepository;
    private final DBNurseRepository nurseJpaRepository;

    public MedicalStaffAppointmentAdaptor(
            DBMedicalStaffAppointmentRepository jpaRepository,
            DBDoctorRepository doctorJpaRepository,
            DBNurseRepository nurseJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.doctorJpaRepository = doctorJpaRepository;
        this.nurseJpaRepository = nurseJpaRepository;
    }

    @Override
    public void save(MedicalStaffAppointment domain) {
        String staffId = domain.getMedicalStaffID();
        String doctorId = null;
        String nurseId = null;

        if (staffId != null) {
            Long staffIdLong;
            try {
                staffIdLong = Long.valueOf(staffId);
            } catch (NumberFormatException e) {
                throw new RuntimeException("ID-ul Personalului Medical este invalid.");
            }

            if (doctorJpaRepository.existsById(staffIdLong)) {
                doctorId = staffId;
            } else if (nurseJpaRepository.existsById(staffIdLong)) {
                nurseId = staffId;
            } else {
                throw new RuntimeException("ID-ul Personalului Medical (" + staffId + ") nu a fost găsit ca Doctor sau Asistentă.");
            }
        }

        jpaRepository.save(Objects.requireNonNull(MedicalStaffAppointmentMapper.createEntityFromIds(
                domain.getAppointmentID(),
                doctorId,
                nurseId
        )));
    }

    @Override
    public void delete(MedicalStaffAppointment domain) {
        if (domain.getMedicalStaffAppointmentID() != null) {
            try {
                jpaRepository.deleteById(Long.valueOf(domain.getMedicalStaffAppointmentID()));
            } catch (NumberFormatException e) { /* Ignoră */ }
        }
    }

    @Override
    public MedicalStaffAppointment findById(String id) { return null; }

    @Override
    public List<MedicalStaffAppointment> findAll() { return List.of(); }
}