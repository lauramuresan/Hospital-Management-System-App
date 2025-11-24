package com.example.Hospital.Management.System.Model.DBModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Numele pacientului este obligatoriu.")
    private String patientName;

    @NotBlank(message = "Adresa de email este obligatorie.")
    @Email(message = "Format email invalid.")
    @Column(unique = true)
    private String pacientEmail;

    @NotNull(message = "Data nașterii este obligatorie.")
    private LocalDate patientBirthDate;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentEntity> appointmentList = new ArrayList<>();

    public PatientEntity() {}

    public PatientEntity(String patientName, String pacientEmail, LocalDate patientBirthDate) {
        this.patientName = patientName;
        this.pacientEmail = pacientEmail;
        this.patientBirthDate = patientBirthDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getPacientEmail() { return pacientEmail; }
    public void setPacientEmail(String pacientEmail) { this.pacientEmail = pacientEmail; }
    public LocalDate getPatientBirthDate() { return patientBirthDate; }
    public void setPatientBirthDate(LocalDate patientBirthDate) { this.patientBirthDate = patientBirthDate; }
    public List<AppointmentEntity> getAppointmentList() { return appointmentList; }
    public void setAppointmentList(List<AppointmentEntity> appointmentList) { this.appointmentList = appointmentList; }
}