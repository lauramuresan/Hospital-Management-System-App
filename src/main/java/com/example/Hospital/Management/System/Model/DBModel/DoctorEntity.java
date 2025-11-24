package com.example.Hospital.Management.System.Model.DBModel;

import com.example.Hospital.Management.System.Model.Enums.MedicalSpecialty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "staff_id")
public class DoctorEntity extends MedicalStaffEntity {

    @NotBlank(message = "Numărul licenței este obligatoriu.")
    @Column(unique = true)
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private MedicalSpecialty medicalSpeciality;

    // Relația JPA (OBLIGATORIE)
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalStaffAppointmentEntity> doctorAppointments = new ArrayList<MedicalStaffAppointmentEntity>();

    // --- Constructor Obligatoriu JPA ---
    public DoctorEntity() {
        super();
    }

    // --- Constructor Utilitar ---
    public DoctorEntity(String staffName, String staffEmail, DepartmentEntity department,
                        String licenseNumber, MedicalSpecialty medicalSpeciality) {
        super(staffName, staffEmail, department);
        this.licenseNumber = licenseNumber;
        this.medicalSpeciality = medicalSpeciality;
    }

    // --- Implementare Metodă Moștenită ---
    @Override
    public List<MedicalStaffAppointmentEntity> getMedicalStaffAppointments() {
        return this.doctorAppointments;
    }

    // --- Getteri și Setteri (rămân doar pentru câmpurile specifice) ---
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public MedicalSpecialty getMedicalSpeciality() { return medicalSpeciality; }
    public void setMedicalSpeciality(MedicalSpecialty medicalSpeciality) { this.medicalSpeciality = medicalSpeciality; }

    // Setter-ul listei rămâne (este util)
    public void setDoctorAppointments(List<MedicalStaffAppointmentEntity> doctorAppointments) { this.doctorAppointments = doctorAppointments; }
}