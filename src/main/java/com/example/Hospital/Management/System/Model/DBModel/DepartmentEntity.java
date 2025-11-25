package com.example.Hospital.Management.System.Model.DBModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Numele departamentului este obligatoriu.")
    private String departmentName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalStaffEntity> medicalStaff = new ArrayList<>();


    public DepartmentEntity() {
    }
    public DepartmentEntity(String departmentName, HospitalEntity hospital) {
        this.departmentName = departmentName;
        this.hospital = hospital;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public HospitalEntity getHospital() {
        return hospital;
    }
    public void setHospital(HospitalEntity hospital) {
        this.hospital = hospital;
    }
    public List<MedicalStaffEntity> getMedicalStaff() {
        return medicalStaff;
    }
    public void setMedicalStaff(List<MedicalStaffEntity> medicalStaff) {
        this.medicalStaff = medicalStaff;
    }
}