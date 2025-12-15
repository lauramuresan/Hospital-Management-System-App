package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.AppointmentMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.AppointmentEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.SearchCriteria.AppointmentSearchCriteria; // IMPORT
import com.example.Hospital.Management.System.Repository.JPA.AppointmentSpecification; // IMPORT
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBAppointmentRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBPatientRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBRoomRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AppointmentAdaptor implements AbstractRepository<Appointment> {

    private final DBAppointmentRepository jpaRepository;
    private final DBRoomRepository roomJpaRepository;
    private final DBPatientRepository patientJpaRepository;
    private final AppointmentMapper mapper;

    public AppointmentAdaptor(DBAppointmentRepository jpaRepository,
                              DBRoomRepository roomJpaRepository,
                              DBPatientRepository patientJpaRepository,
                              AppointmentMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.roomJpaRepository = roomJpaRepository;
        this.patientJpaRepository = patientJpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void save(Appointment domain) {
        if (domain.getAdmissionDate() != null && domain.getAdmissionDate().isBefore(LocalDateTime.now().minusMinutes(1))) {
            throw new RuntimeException("Programarea nu poate fi stabilita pentru o data/ora din trecut.");
        }

        Long roomId = MapperUtils.parseLong(domain.getRoomID());
        Long patientId = MapperUtils.parseLong(domain.getPatientID());

        if (roomId == null || !roomJpaRepository.existsById(roomId)) {
            throw new RuntimeException("Camera specificata nu exista sau ID-ul este invalid.");
        }
        if (patientId == null || !patientJpaRepository.existsById(patientId)) {
            throw new RuntimeException("Pacientul specificat nu exista sau ID-ul este invalid.");
        }

        if (isRoomOccupied(roomId, domain.getAdmissionDate(), domain.getAppointmentID())) {
            throw new RuntimeException("Camera " + domain.getRoomID() + " este ocupata la data si ora specificata.");
        }

        jpaRepository.save(mapper.toEntity(domain)); // Folosim instanța mapper
    }

    private boolean isRoomOccupied(Long roomId, LocalDateTime appointmentDateTime, String currentAppointmentId) {
        LocalDateTime startTime = appointmentDateTime;
        LocalDateTime endTime = appointmentDateTime.plusHours(1);
        LocalDateTime startWindow = startTime.minusHours(1).plusMinutes(1);
        LocalDateTime endWindow = endTime.minusMinutes(1);

        List<AppointmentEntity> overlappingAppointments = jpaRepository.findByRoomIdAndAppointmentDateTimeBetween(
                roomId, startWindow, endWindow
        );

        if (currentAppointmentId != null) {
            Long currentId = MapperUtils.parseLong(currentAppointmentId);
            overlappingAppointments.removeIf(app -> app.getId() != null && app.getId().equals(currentId));
        }

        return !overlappingAppointments.isEmpty();
    }

    @Override
    public void delete(Appointment domain) {
        if (domain.getAppointmentID() != null) {
            jpaRepository.deleteById(MapperUtils.parseLong(domain.getAppointmentID()));
        }
    }

    @Override
    public Appointment findById(String id) {
        try {
            return jpaRepository.findById(MapperUtils.parseLong(id))
                    .map(mapper::toDomain) // Folosim instanța mapper
                    .orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Appointment> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findAll(Sort sort) {
        return findAll(null, sort);
    }

    @Override
    public List<Appointment> findAll(Object searchCriteria, Sort sort) {
        Specification<AppointmentEntity> spec = null;

        if (searchCriteria instanceof AppointmentSearchCriteria) {
            spec = AppointmentSpecification.filterByCriteria((AppointmentSearchCriteria) searchCriteria);
        }

        return jpaRepository.findAll(spec, sort).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}