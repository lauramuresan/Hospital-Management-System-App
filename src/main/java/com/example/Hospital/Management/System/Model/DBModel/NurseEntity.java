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
    @Enumerated(EnumType.STRING)
    private NurseLevelQualification nurseCategory;

    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalStaffAppointmentEntity> nurseAppointments = new ArrayList<MedicalStaffAppointmentEntity>();

    public NurseEntity() {
        super();
    }

    public NurseEntity(String staffName, String staffEmail, DepartmentEntity department, NurseLevelQualification nurseCategory) {
        super(staffName, staffEmail, department);
        this.nurseCategory = nurseCategory;
    }

    @Override
    public List<MedicalStaffAppointmentEntity> getMedicalStaffAppointments() {
        return this.nurseAppointments;
    }
    public NurseLevelQualification getNurseCategory() { return nurseCategory; }
    public void setNurseCategory(NurseLevelQualification nurseCategory) { this.nurseCategory = nurseCategory; }
    public void setNurseAppointments(List<MedicalStaffAppointmentEntity> nurseAppointments) { this.nurseAppointments = nurseAppointments; }
}