package com.example.Hospital.Management.System.Model;

public class Department {
    private String departmentID;
    private String departmentName;
    private String hospitalID;
    public Department(String departmentID, String departmentName, String hospitalID) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.hospitalID = hospitalID;
    }
    public String getDepartmentID() {
        return departmentID;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public String getHospitalID() {
        return hospitalID;
    }
    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }
    @Override
    public String toString() {
        return "Department{" +
                "departmentID='" + departmentID + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", hospitalID='" + hospitalID + '\'' +
                '}';
    }
}
