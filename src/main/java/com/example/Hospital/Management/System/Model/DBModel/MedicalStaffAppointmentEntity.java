package com.example.Hospital.Management.System.Model.DBModel;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "medical_staff_appointment")
public class MedicalStaffAppointmentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private AppointmentEntity appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id")
    private NurseEntity nurse;

    public MedicalStaffAppointmentEntity() {}

    public MedicalStaffAppointmentEntity(AppointmentEntity appointment, DoctorEntity doctor, NurseEntity nurse) {
        this.appointment = appointment;
        this.doctor = doctor;
        this.nurse = nurse;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public AppointmentEntity getAppointment() { return appointment; }
    public void setAppointment(AppointmentEntity appointment) { this.appointment = appointment; }
    public DoctorEntity getDoctor() { return doctor; }
    public void setDoctor(DoctorEntity doctor) { this.doctor = doctor; }
    public NurseEntity getNurse() { return nurse; }
    public void setNurse(NurseEntity nurse) { this.nurse = nurse; }

}