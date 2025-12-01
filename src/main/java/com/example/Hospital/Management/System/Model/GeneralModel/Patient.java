package com.example.Hospital.Management.System.Model.GeneralModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String patientID;
    private String patientName;
    private List<Appointment> appointmentList = new ArrayList<>();
    private String pacientEmail;
    private LocalDate patientBirthDate;

    public Patient() {
        this.appointmentList = new ArrayList<>();
    }

    public Patient(String patientID, String patientName, List<Appointment> appointmentList,
                   String pacientEmail, LocalDate patientBirthDate) {
        this.patientID = patientID;
        this.patientName = patientName;
        this.appointmentList = appointmentList != null ? appointmentList : new ArrayList<>();
        this.pacientEmail = pacientEmail;
        this.patientBirthDate = patientBirthDate;
    }

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

    public LocalDate getPatientBirthDate() { return patientBirthDate; }
    public void setPatientBirthDate(LocalDate patientBirthDate) { this.patientBirthDate = patientBirthDate; }

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