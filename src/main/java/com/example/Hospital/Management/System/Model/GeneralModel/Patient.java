package com.example.Hospital.Management.System.Model.GeneralModel;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String patientID;
    private String patientName;
    private List<Appointment> appointmentList = new ArrayList<>(); // INITIALIZEAZĂ lista!
    private String pacientEmail;
    private String patientBirthDate;

    // Constructor implicit
    public Patient() {
        this.appointmentList = new ArrayList<>(); // asigură-te că lista e inițializată
    }

    public Patient(String patientID, String patientName, List<Appointment> appointmentList,
                   String pacientEmail, String patientBirthDate) {
        this.patientID = patientID;
        this.patientName = patientName;
        this.appointmentList = appointmentList != null ? appointmentList : new ArrayList<>();
        this.pacientEmail = pacientEmail;
        this.patientBirthDate = patientBirthDate;
    }

    // Getters și Setters
    public String getPatientID() { return patientID; }
    public void setPatientID(String patientID) { this.patientID = patientID; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public List<Appointment> getAppointmentList() { return appointmentList; }
    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList != null ? appointmentList : new ArrayList<>();
    }

    public String getPacientEmail() { return pacientEmail; }
    public void setPacientEmail(String pacientEmail) { this.pacientEmail = pacientEmail; }

    public String getPatientBirthDate() { return patientBirthDate; }
    public void setPatientBirthDate(String patientBirthDate) { this.patientBirthDate = patientBirthDate; }

    @Override
    public String toString() {
        return "Patient{" +
                "patientID='" + patientID + '\'' +
                ", patientName='" + patientName + '\'' +
                ", pacientEmail='" + pacientEmail + '\'' +
                ", patientBirthDate='" + patientBirthDate + '\'' +
                '}';
    }
}