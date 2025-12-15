package com.example.Hospital.Management.System.SearchCriteria;

import com.example.Hospital.Management.System.Model.Enums.MedicalSpecialty;

public class DoctorSearchCriteria {

    private String staffName;
    private String staffEmail;
    private MedicalSpecialty medicalSpeciality;
    private String departmentID;

    public DoctorSearchCriteria() {}

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public MedicalSpecialty getMedicalSpeciality() {
        return medicalSpeciality;
    }

    public void setMedicalSpeciality(MedicalSpecialty medicalSpeciality) {
        this.medicalSpeciality = medicalSpeciality;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }
}