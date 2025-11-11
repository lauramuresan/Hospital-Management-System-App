package com.example.Hospital.Management.System.Model;

import com.example.Hospital.Management.System.Model.Enums.MedicalSpecialty;

import java.util.List;

public class Doctor extends MedicalStaff{
    private String licenseNumber;
    private MedicalSpecialty medicalSpeciality;
    public Doctor() {
        super();
    }
    public Doctor(String staffID, String departmentID, String staffName, List<Appointment> appointments,String staffEmail, String licenseNumber,MedicalSpecialty medicalSpeciality) {
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
    public MedicalSpecialty getMedicalSpeciality() {return medicalSpeciality;}
    public void setMedicalSpeciality(MedicalSpecialty medicalSpeciality) {this.medicalSpeciality = medicalSpeciality;}

    @Override
    public String toString() {
        return "Doctor{" +
                "licenseNumber='" + licenseNumber + '\'' +
                ", medicalSpeciality='" + medicalSpeciality + '\'' +
                ", appointments=" + getAppointments() +
                '}';
    }
}