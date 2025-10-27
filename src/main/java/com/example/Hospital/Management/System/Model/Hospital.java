package com.example.Hospital.Management.System.Model;
import java.util.ArrayList;
import java.util.List;

public class Hospital {
    private String hospitalID;
    private String hospitalName;
    private String city;
    private List<Department> departments;
    private List<Room> rooms;
    public Hospital(String hospitalID, String hospitalName, String city) {
        this.hospitalID = hospitalID;
        this.hospitalName = hospitalName;
        this.city = city;
        this.departments = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }
    public String getHospitalID() {
        return hospitalID;
    }
    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setDepartments(List<Department> departments) {
        departments = departments;
    }
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
    public String getHospitalName() {
        return hospitalName;
    }
    public String getCity() {
        return city;
    }
    public List<Department> getDepartments() {
        return departments;
    }
    public List<Room> getRooms() {
        return rooms;
    }
    @Override
    public String toString() {
        return "Hospital{" +
                "hospitalID='" + hospitalID + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", city='" + city + '\'' +
                ", departments=" + departments +
                ", rooms=" + rooms +
                '}';
    }
}
