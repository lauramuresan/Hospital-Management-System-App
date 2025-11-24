package com.example.Hospital.Management.System.Model.DBModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hospitals")
public class HospitalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 150)
    private String hospitalName;

    @NotBlank
    private String city;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DepartmentEntity> departments = new ArrayList<>();

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomEntity> rooms = new ArrayList<>();

    public HospitalEntity() {

    }
    public HospitalEntity(String hospitalName, String city, List<DepartmentEntity> departments, List<RoomEntity> rooms) {
        this.hospitalName = hospitalName;
        this.city = city;
        this.departments = departments;
        this.rooms = rooms;
    }

    public Long getId() {
        return id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getCity() {
        return city;
    }

    public List<DepartmentEntity> getDepartments() {
        return departments;
    }

    public List<RoomEntity> getRooms() {
        return rooms;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDepartments(List<DepartmentEntity> departments) {
        this.departments = departments;
    }

    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }
}