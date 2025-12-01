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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentAdaptor implements AbstractRepository<Appointment> {

    private final DBAppointmentRepository jpaRepository;
    private final DBPatientRepository patientJpaRepository;
    private final DBRoomRepository roomJpaRepository;

    public AppointmentAdaptor(DBAppointmentRepository jpaRepository,
                              DBPatientRepository patientJpaRepository,
                              DBRoomRepository roomJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.patientJpaRepository = patientJpaRepository;
        this.roomJpaRepository = roomJpaRepository;
    }

    @Override
    public void save(Appointment domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Programarea");

        // 1. Validare FK (Patient & Room)
        Long patientId = RepositoryValidationUtils.parseIdOrThrow(domain.getPatientID(), "ID-ul Pacientului este invalid sau lipsește.");
        Long roomId = RepositoryValidationUtils.parseIdOrThrow(domain.getRoomID(), "ID-ul Camerei este invalid sau lipsește.");

        if (!patientJpaRepository.existsById(patientId)) {
            throw new RuntimeException("Pacientul cu ID-ul " + patientId + " nu există. Vă rugăm selectați un pacient valid.");
        }
        if (!roomJpaRepository.existsById(roomId)) {
            throw new RuntimeException("Camera cu ID-ul " + roomId + " nu există. Vă rugăm selectați o cameră validă.");
        }

        // 2. Validare Suprapunere (Business Logic)
        if (isRoomOccupied(roomId, domain.getAdmissionDate(), domain.getAppointmentID())) {
            throw new RuntimeException("Camera " + domain.getRoomID() + " este ocupată la data și ora specificată. Vă rugăm alegeți alt interval (presupus de 1 oră).");
        }

        jpaRepository.save(AppointmentMapper.toEntity(domain));
    }

    private boolean isRoomOccupied(Long roomId, LocalDateTime appointmentDateTime, String currentAppointmentId) {
        // Intervalul noii programări: [startTime, endTime)
        LocalDateTime startTime = appointmentDateTime;
        LocalDateTime endTime = appointmentDateTime.plusHours(1);

        // Fereastra de căutare pentru programările existente care ar putea începe și suprapune intervalul
        LocalDateTime startWindow = startTime.minusHours(1).plusMinutes(1);
        LocalDateTime endWindow = endTime.minusMinutes(1);

        List<AppointmentEntity> overlappingAppointments = jpaRepository.findByRoomIdAndAppointmentDateTimeBetween(
                roomId, startWindow, endWindow
        );

        if (currentAppointmentId != null && !currentAppointmentId.isBlank()) {
            Long currentId = MapperUtils.parseLong(currentAppointmentId);
            overlappingAppointments.removeIf(app -> app.getId() != null && app.getId().equals(currentId));
        }

        return !overlappingAppointments.isEmpty();
    }


    @Override
    public void delete(Appointment domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Programarea");
        RepositoryValidationUtils.requireIdForDelete(domain.getAppointmentID(), "ID-ul programării");

        Long id = RepositoryValidationUtils.parseIdOrThrow(domain.getAppointmentID(), "ID-ul programării este invalid.");
        jpaRepository.deleteById(id);
    }

    @Override
    public Appointment findById(String id) {
        Long parsed = RepositoryValidationUtils.parseIdOrNull(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed).map(AppointmentMapper::toDomain).orElse(null);
    }

    @Override
    public List<Appointment> findAll() {
        // ✅ CORECTAT: Utilizează metoda optimizată JOIN FETCH
        return jpaRepository.findAllWithPatientAndRoom().stream()
                .map(AppointmentMapper::toDomain)
                .collect(Collectors.toList());
    }
}