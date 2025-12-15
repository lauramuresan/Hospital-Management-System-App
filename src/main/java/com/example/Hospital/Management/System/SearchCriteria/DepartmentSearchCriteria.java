package com.example.Hospital.Management.System.SearchCriteria;

public class DepartmentSearchCriteria {

    private String departmentName;
    private String hospitalID;

    public DepartmentSearchCriteria() {}

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }
}