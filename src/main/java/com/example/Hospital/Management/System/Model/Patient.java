package com.example.Hospital.Management.System.Model;
import java.util.List;
import java.time.LocalDate;

public class Patient {
    private String patientID;
    private String patientName;
    private List<Appointment> appointmentList;
    private String pacientEmail;
    private LocalDate dateOfBirth;
    public Patient(String patientID, String patientName, List<Appointment> appointmentList, String pacientEmail, LocalDate dateOfBirth) {
        this.patientID = patientID;
        this.patientName = patientName;
        this.appointmentList = appointmentList;
        this.pacientEmail = pacientEmail;
        this.dateOfBirth = dateOfBirth;
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
    public LocalDate getPatientBirthDate() { return  dateOfBirth; }
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
    public void setPatientBirthAge(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    @Override
    public String toString() {
        return "Patient{" +
                "patientID='" + patientID + '\'' +
                ", patientName='" + patientName + '\'' +
                ", appointmentList=" + appointmentList +
                ", pacientEmail='" + pacientEmail + '\'' +
                ", patientDateOfBirth=" + dateOfBirth +
                '}';
    }
}