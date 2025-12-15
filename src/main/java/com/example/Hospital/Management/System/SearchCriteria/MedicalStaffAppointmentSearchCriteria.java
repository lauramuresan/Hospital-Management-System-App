package com.example.Hospital.Management.System.SearchCriteria;

public class MedicalStaffAppointmentSearchCriteria {

    private String appointmentID;
    private String medicalStaffID;

    public MedicalStaffAppointmentSearchCriteria() {}

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getMedicalStaffID() {
        return medicalStaffID;
    }

    public void setMedicalStaffID(String medicalStaffID) {
        this.medicalStaffID = medicalStaffID;
    }
}