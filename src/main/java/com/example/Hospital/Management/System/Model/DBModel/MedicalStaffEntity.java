package com.example.Hospital.Management.System.Model.DBModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "medical_staff")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MedicalStaffEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Numele este obligatoriu.")
    private String staffName;

    @NotBlank(message = "Emailul este obligatoriu.")
    @Email(message = "Format email invalid.")
    @Column(unique = true)
    private String staffEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity department;

    // --- Metoda Moștenită (Abstractă) ---
    public abstract List<MedicalStaffAppointmentEntity> getMedicalStaffAppointments();

    // --- Constructor Obligatoriu JPA ---
    public MedicalStaffEntity() {}

    // --- Constructor Utilitar ---
    public MedicalStaffEntity(String staffName, String staffEmail, DepartmentEntity department) {
        this.staffName = staffName;
        this.staffEmail = staffEmail;
        this.department = department;
    }

    // --- Getteri și Setteri ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    public String getStaffEmail() { return staffEmail; }
    public void setStaffEmail(String staffEmail) { this.staffEmail = staffEmail; }
    public DepartmentEntity getDepartment() { return department; }
    public void setDepartment(DepartmentEntity department) { this.department = department; }
}