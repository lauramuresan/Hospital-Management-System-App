package com.example.Hospital.Management.System.Mapper;

import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import com.example.Hospital.Management.System.Model.GeneralModel.Nurse;
import com.example.Hospital.Management.System.Model.DBModel.AppointmentEntity;
import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;
import com.example.Hospital.Management.System.Model.DBModel.RoomEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AppointmentMapper {


    public AppointmentEntity toEntity(Appointment domain) {
        if (domain == null) return null;
        AppointmentEntity entity = new AppointmentEntity();
        entity.setId(domain.getAppointmentID() != null ? MapperUtils.parseLong(domain.getAppointmentID()) : null);
        entity.setAdmissionDate(domain.getAdmissionDate());
        entity.setStatus(domain.getStatus());
        entity.setPatient(MapperUtils.createEntityProxy(PatientEntity.class, domain.getPatientID()));
        entity.setRoom(MapperUtils.createEntityProxy(RoomEntity.class, domain.getRoomID()));
        return entity;
    }


    public Appointment toDomain(AppointmentEntity entity) {
        if (entity == null) return null;

        Appointment domain = new Appointment();
        domain.setAppointmentID(entity.getId() != null ? String.valueOf(entity.getId()) : null);
        domain.setAdmissionDate(entity.getAdmissionDate());
        domain.setStatus(entity.getStatus());

        if (entity.getPatient() != null && entity.getPatient().getId() != null)
            domain.setPatientID(String.valueOf(entity.getPatient().getId()));

        if (entity.getRoom() != null && entity.getRoom().getId() != null)
            domain.setRoomID(String.valueOf(entity.getRoom().getId()));

        if (entity.getStaffList() != null) {
            domain.setMedicalStaffList(entity.getStaffList().stream()
                    .map(msa -> {
                        if (msa.getDoctor() != null) {
                            Doctor d = new Doctor();
                            d.setStaffID(String.valueOf(msa.getDoctor().getId()));
                            return d;
                        } else if (msa.getNurse() != null) {
                            Nurse n = new Nurse();
                            n.setStaffID(String.valueOf(msa.getNurse().getId()));
                            return n;
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        } else {
            domain.setMedicalStaffList(Collections.emptyList());
        }

        return domain;
    }
}