package com.example.Hospital.Management.System.Model.DBModel;

import com.example.Hospital.Management.System.Model.Enums.RoomAvailability;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Numărul camerei este obligatoriu.")
    @Column(unique = true)
    private String number;

    @Min(value = 1, message = "Capacitatea minimă este 1.")
    @NotNull(message = "Capacitatea este obligatorie.")
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Statusul camerei este obligatoriu.")
    private RoomAvailability status;

    // Relație One-to-Many cu AppointmentEntity
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentEntity> appointments = new ArrayList<>();

    public RoomEntity() {
    }


    public RoomEntity(String number, Integer capacity, HospitalEntity hospital, RoomAvailability status) {
        this.number = number;
        this.capacity = capacity;
        this.hospital = hospital;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public HospitalEntity getHospital() {
        return hospital;
    }

    public void setHospital(HospitalEntity hospital) {
        this.hospital = hospital;
    }

    public RoomAvailability getStatus() {
        return status;
    }

    public void setStatus(RoomAvailability status) {
        this.status = status;
    }

    public List<AppointmentEntity> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentEntity> appointments) {
        this.appointments = appointments;
    }
}