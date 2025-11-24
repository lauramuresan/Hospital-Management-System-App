package com.example.Hospital.Management.System.Model.DBModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "departments")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Cheia primară (Long)

    @NotBlank(message = "Numele departamentului este obligatoriu.")
    private String departmentName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    public DepartmentEntity() {

    }
    public DepartmentEntity(String departmentName, HospitalEntity hospital) {
        this.departmentName = departmentName;
        this.hospital = hospital;
    }

    public Long getId() {
        return id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public HospitalEntity getHospital() {
        return hospital;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setHospital(HospitalEntity hospital) {
        this.hospital = hospital;
    }
}