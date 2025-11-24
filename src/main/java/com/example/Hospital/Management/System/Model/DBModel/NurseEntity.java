package com.example.Hospital.Management.System.Model.DBModel;

import com.example.Hospital.Management.System.Model.Enums.NurseLevelQualification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nurses")
@PrimaryKeyJoinColumn(name = "staff_id")
public class NurseEntity extends MedicalStaffEntity {

    @NotBlank(message = "Categoria este obligatorie.")
    private String nurseCategory;

    // Relația JPA (OBLIGATORIE)
    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalStaffAppointmentEntity> nurseAppointments = new ArrayList<MedicalStaffAppointmentEntity>();

    // --- Constructor Obligatoriu JPA ---
    public NurseEntity() {
        super();
    }

    // --- Constructor Utilitar ---
    public NurseEntity(String staffName, String staffEmail, DepartmentEntity department, String nurseCategory) {
        super(staffName, staffEmail, department);
        this.nurseCategory = nurseCategory;
    }

    // --- Implementare Metodă Moștenită ---
    @Override
    public List<MedicalStaffAppointmentEntity> getMedicalStaffAppointments() {
        return this.nurseAppointments;
    }

    // --- Getteri și Setteri (rămân doar pentru câmpurile specifice) ---
    public NurseLevelQualification getNurseCategory() { return nurseCategory; }
    public void setNurseCategory(String nurseCategory) { this.nurseCategory = nurseCategory; }

    // Setter-ul listei rămâne (este util)
    public void setNurseAppointments(List<MedicalStaffAppointmentEntity> nurseAppointments) { this.nurseAppointments = nurseAppointments; }
}