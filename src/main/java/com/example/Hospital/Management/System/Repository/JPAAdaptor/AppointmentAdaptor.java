package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.AppointmentMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.AppointmentEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBAppointmentRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBPatientRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBRoomRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentAdaptor implements AbstractRepository<Appointment> {

    private final DBAppointmentRepository jpaRepository;
    private final DBRoomRepository roomJpaRepository;
    private final DBPatientRepository patientJpaRepository;

    public AppointmentAdaptor(DBAppointmentRepository jpaRepository, DBRoomRepository roomJpaRepository, DBPatientRepository patientJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.roomJpaRepository = roomJpaRepository;
        this.patientJpaRepository = patientJpaRepository;
    }

    @Override
    @Transactional
    public void save(Appointment domain) {

        // 1. Validare Data/Ora (Business Validation: trecut)
        if (domain.getAdmissionDate() != null && domain.getAdmissionDate().isBefore(LocalDateTime.now().minusMinutes(1))) {
            throw new RuntimeException("Programarea nu poate fi stabilită pentru o dată/oră din trecut.");
        }

        Long roomId = MapperUtils.parseLong(domain.getRoomID());
        Long patientId = MapperUtils.parseLong(domain.getPatientID());

        // 2. Validare existență FK (ID invalid sau inexistent)
        if (roomId == null || !roomJpaRepository.existsById(roomId)) {
            throw new RuntimeException("Camera specificată nu există sau ID-ul este invalid.");
        }
        if (patientId == null || !patientJpaRepository.existsById(patientId)) {
            throw new RuntimeException("Pacientul specificat nu există sau ID-ul este invalid.");
        }

        // 3. Validare Suprapunere Programări
        if (isRoomOccupied(roomId, domain.getAdmissionDate(), domain.getAppointmentID())) {
            throw new RuntimeException("Camera " + domain.getRoomID() + " este ocupată la data și ora specificată.");
        }

        jpaRepository.save(AppointmentMapper.toEntity(domain));
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
                    .map(AppointmentMapper::toDomain)
                    .orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Appointment> findAll() {
        return jpaRepository.findAll().stream()
                .map(AppointmentMapper::toDomain)
                .collect(Collectors.toList());
    }
}