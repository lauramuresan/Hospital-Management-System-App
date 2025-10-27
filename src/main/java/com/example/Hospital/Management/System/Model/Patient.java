package com.example.Hospital.Management.System.Model;

import java.util.List;

public class Patient {
    private String patientID;
    private String patientName;
    private List<Appointment> appointmentList;
    private String pacientEmail;
    private int patientAge;

    public Patient(String patientID, String patientName, List<Appointment> appointmentList, String pacientEmail, int patientAge) {
        this.patientID = patientID;
        this.patientName = patientName;
        this.appointmentList = appointmentList;
        this.pacientEmail = pacientEmail;
        this.patientAge = patientAge;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public String getPacientEmail() {
        return pacientEmail;
    }

    public int getPatientAge() { return  patientAge; }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public void setPacientEmail(String pacientEmail) { this.pacientEmail = pacientEmail; }

    public void setPatientAge(int patientAge) { this.patientAge = patientAge; }
}