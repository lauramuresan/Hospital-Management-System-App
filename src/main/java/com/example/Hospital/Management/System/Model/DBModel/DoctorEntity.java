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
    @Column(unique = true, nullable = false) // <<< ADĂUGAT: Forțează NOT NULL în DB
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false) // ADĂUGAT: Specialitatea este obligatorie
    private MedicalSpecialty medicalSpeciality;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalStaffAppointmentEntity> doctorAppointments = new ArrayList<MedicalStaffAppointmentEntity>();


    public DoctorEntity() {
        super();
    }

    public DoctorEntity(String staffName, String staffEmail, DepartmentEntity department,
                        String licenseNumber, MedicalSpecialty medicalSpeciality) {
        super(staffName, staffEmail, department);
        this.licenseNumber = licenseNumber;
        this.medicalSpeciality = medicalSpeciality;
    }

    @Override
    public List<MedicalStaffAppointmentEntity> getMedicalStaffAppointments() {
        return this.doctorAppointments;
    }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public MedicalSpecialty getMedicalSpeciality() { return medicalSpeciality; }
    public void setMedicalSpeciality(MedicalSpecialty medicalSpeciality) { this.medicalSpeciality = medicalSpeciality; }
    public void setDoctorAppointments(List<MedicalStaffAppointmentEntity> doctorAppointments) { this.doctorAppointments = doctorAppointments; }
}