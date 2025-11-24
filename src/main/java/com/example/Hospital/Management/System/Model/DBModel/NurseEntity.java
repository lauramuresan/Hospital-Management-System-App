package com.example.Hospital.Management.System.Model.DBModel;

import com.example.Hospital.Management.System.Model.Enums.NurseLevelQualification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull; // Folosim doar NotNull pentru Enum
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nurses")
@PrimaryKeyJoinColumn(name = "staff_id")
public class NurseEntity extends MedicalStaffEntity {

    @NotNull(message = "Categoria este obligatorie.")
    @Enumerated(EnumType.STRING) // Esențial pentru a stoca Enum-ul ca String în DB
    private NurseLevelQualification nurseCategory;

    // Relația JPA (OBLIGATORIE)
    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalStaffAppointmentEntity> nurseAppointments = new ArrayList<MedicalStaffAppointmentEntity>();

    // --- Constructor Obligatoriu JPA ---
    public NurseEntity() {
        super();
    }

    // --- Constructor Utilitar (CORECTAT) ---
    // S-a eliminat @NotBlank de pe NurseLevelQualification
    public NurseEntity(String staffName, String staffEmail, DepartmentEntity department, NurseLevelQualification nurseCategory) {
        super(staffName, staffEmail, department);
        this.nurseCategory = nurseCategory;
    }

    // --- Implementare Metodă Moștenită ---
    @Override
    public List<MedicalStaffAppointmentEntity> getMedicalStaffAppointments() {
        return this.nurseAppointments;
    }

    // --- Getteri și Setteri (CORECTAT) ---
    public NurseLevelQualification getNurseCategory() { return nurseCategory; }

    // S-a eliminat @NotBlank de pe Setter, s-a păstrat @NotNull pentru coerență dacă doriți
    public void setNurseCategory(NurseLevelQualification nurseCategory) { this.nurseCategory = nurseCategory; }

    // Setter-ul listei rămâne (este util)
    public void setNurseAppointments(List<MedicalStaffAppointmentEntity> nurseAppointments) { this.nurseAppointments = nurseAppointments; }
}