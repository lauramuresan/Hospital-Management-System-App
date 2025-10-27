package com.example.Hospital.Management.System.Model;

import java.util.List;

public class Doctor extends MedicalStaff{
    private String licenseNumber;
    private String medicalSpeciality;
    public Doctor(String staffID, String departmentID, String staffName, List<Appointment> appointments,String staffEmail, String licenseNumber,String medicalSpeciality) {
        super(staffID, departmentID, staffName, appointments, staffEmail);
        this.licenseNumber = licenseNumber;
        this.medicalSpeciality = medicalSpeciality;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    public String getMedicalSpeciality() {return medicalSpeciality;}
    public void setMedicalSpeciality(String medicalSpeciality) {this.medicalSpeciality = medicalSpeciality;}
}