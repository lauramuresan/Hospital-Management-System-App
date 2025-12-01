package com.example.Hospital.Management.System.Model.DBModel;

import com.example.Hospital.Management.System.Model.Enums.AppointmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data și ora programării sunt obligatorii.")
    @Column(name = "appointment_date_time")
    private LocalDateTime admissionDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Statusul programării este obligatoriu.")
    private AppointmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalStaffAppointmentEntity> staffList = new ArrayList<>();

    public AppointmentEntity() {}

    public AppointmentEntity(LocalDateTime admissionDate, AppointmentStatus status, PatientEntity patient, RoomEntity room) {
        this.admissionDate = admissionDate;
        this.status = status;
        this.patient = patient;
        this.room = room;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDateTime appointmentDateTime) { this.admissionDate = appointmentDateTime; }
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    public PatientEntity getPatient() { return patient; }
    public void setPatient(PatientEntity patient) { this.patient = patient; }
    public RoomEntity getRoom() { return room; }
    public void setRoom(RoomEntity room) { this.room = room; }
    public List<MedicalStaffAppointmentEntity> getStaffList() { return staffList; }
    public void setStaffList(List<MedicalStaffAppointmentEntity> staffList) { this.staffList = staffList; }
}