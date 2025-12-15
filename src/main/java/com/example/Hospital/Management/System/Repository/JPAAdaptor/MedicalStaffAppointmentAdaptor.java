package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Mapper.MedicalStaffAppointmentMapper;
import com.example.Hospital.Management.System.Model.DBModel.AppointmentEntity;
import com.example.Hospital.Management.System.Model.DBModel.DoctorEntity;
import com.example.Hospital.Management.System.Model.DBModel.MedicalStaffAppointmentEntity;
import com.example.Hospital.Management.System.Model.DBModel.NurseEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaffAppointment;
import com.example.Hospital.Management.System.SearchCriteria.MedicalStaffAppointmentSearchCriteria;
import com.example.Hospital.Management.System.Repository.JPA.MedicalStaffAppointmentSpecification;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBAppointmentRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBDoctorRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBMedicalStaffAppointmentRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBNurseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MedicalStaffAppointmentAdaptor implements AbstractRepository<MedicalStaffAppointment> {

    private final DBMedicalStaffAppointmentRepository jpaRepository;
    private final DBAppointmentRepository appointmentRepository;
    private final DBDoctorRepository doctorRepository;
    private final DBNurseRepository nurseRepository;
    private final MedicalStaffAppointmentMapper mapper;

    public MedicalStaffAppointmentAdaptor(
            DBMedicalStaffAppointmentRepository jpaRepository,
            DBAppointmentRepository appointmentRepository,
            DBDoctorRepository doctorRepository,
            DBNurseRepository nurseRepository,
            MedicalStaffAppointmentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void save(MedicalStaffAppointment domain) {
        if (domain == null) throw new IllegalArgumentException("Domeniul nu poate fi null.");

        Long appId = MapperUtils.parseLong(domain.getAppointmentID());
        Long staffId = MapperUtils.parseLong(domain.getMedicalStaffID());

        if (appId == null || !appointmentRepository.existsById(appId)) {
            throw new RuntimeException("Programarea specificată nu există.");
        }

        // Determinăm dacă staffId aparține unui Doctor sau Asistent
        boolean isDoctor = doctorRepository.existsById(staffId);
        boolean isNurse = nurseRepository.existsById(staffId);

        if (!isDoctor && !isNurse) {
            throw new RuntimeException("ID-ul personalului medical nu este valid (nu există nici ca Doctor, nici ca Asistent).");
        }

        // Verificăm duplicat
        if (isDoctor && jpaRepository.existsByAppointmentIdAndDoctorId(appId, staffId)) {
            throw new RuntimeException("Acest doctor este deja alocat la această programare.");
        }
        if (isNurse && jpaRepository.existsByAppointmentIdAndNurseId(appId, staffId)) {
            throw new RuntimeException("Acest asistent este deja alocat la această programare.");
        }

        MedicalStaffAppointmentEntity entity = new MedicalStaffAppointmentEntity();
        if (domain.getMedicalStaffAppointmentID() != null) {
            entity.setId(MapperUtils.parseLong(domain.getMedicalStaffAppointmentID()));
        }

        entity.setAppointment(MapperUtils.createEntityProxy(AppointmentEntity.class, domain.getAppointmentID()));

        if (isDoctor) {
            entity.setDoctor(MapperUtils.createEntityProxy(DoctorEntity.class, domain.getMedicalStaffID()));
            entity.setNurse(null);
        } else {
            entity.setNurse(MapperUtils.createEntityProxy(NurseEntity.class, domain.getMedicalStaffID()));
            entity.setDoctor(null);
        }

        jpaRepository.save(entity);
    }

    @Override
    public void delete(MedicalStaffAppointment domain) {
        if (domain != null && domain.getMedicalStaffAppointmentID() != null) {
            jpaRepository.deleteById(MapperUtils.parseLong(domain.getMedicalStaffAppointmentID()));
        }
    }

    @Override
    public MedicalStaffAppointment findById(String id) {
        Long parsed = MapperUtils.parseLong(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed).map(mapper::toDomain).orElse(null);
    }

    @Override
    public List<MedicalStaffAppointment> findAll() {
        return jpaRepository.findAllWithStaffAndAppointment().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalStaffAppointment> findAll(Sort sort) {
        return findAll(null, sort);
    }

    @Override
    public List<MedicalStaffAppointment> findAll(Object searchCriteria, Sort sort) {
        Specification<MedicalStaffAppointmentEntity> spec = null;

        if (searchCriteria instanceof MedicalStaffAppointmentSearchCriteria) {
            spec = MedicalStaffAppointmentSpecification.filterByCriteria((MedicalStaffAppointmentSearchCriteria) searchCriteria);
        }

        return jpaRepository.findAll(spec, sort).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}