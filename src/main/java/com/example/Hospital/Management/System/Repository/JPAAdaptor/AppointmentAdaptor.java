package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.AppointmentMapper;
import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBAppointmentRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBRoomRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBPatientRepository;
import org.springframework.stereotype.Component;

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
    public void save(Appointment domain) {
        if (domain.getAdmissionDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Programarea nu poate fi stabilită pentru o dată/oră din trecut.");
        }

        // Dacă codul comentat va fi activat, verificați dacă getRoom() returnează un String sau un obiect POJO
        /*
        if (!roomJpaRepository.existsById(Long.valueOf(domain.getRoomID())) || !patientJpaRepository.existsById(Long.valueOf(domain.getPatientID()))) {
            throw new RuntimeException("Camera sau Pacientul specificat nu există.");
        }
        */

        // CORECTAT: Apelăm metoda statică direct pe clasa AppointmentMapper
        jpaRepository.save(AppointmentMapper.toEntity(domain));
    }

    @Override
    public void delete(Appointment domain) {
        if (domain.getAppointmentID() != null) {
            jpaRepository.deleteById(Long.valueOf(domain.getAppointmentID()));
        }
    }

    @Override
    public Appointment findById(String id) {
        try {
            return jpaRepository.findById(Long.valueOf(id))
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