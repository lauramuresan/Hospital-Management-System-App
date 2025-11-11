package com.example.Hospital.Management.System.Model;

public class MedicalStaffAppointment {
    private String medicalStaffAppointmentID;
    private String appointmentID;
    private String medicalStaffID;
    public MedicalStaffAppointment() {
    }
    public MedicalStaffAppointment(String medicalStaffAppointmentID, String appointmentID, String medicalStaffID) {
        this.medicalStaffAppointmentID = medicalStaffAppointmentID;
        this.appointmentID = appointmentID;
        this.medicalStaffID = medicalStaffID;
    }
    public String getMedicalStaffAppointmentID() {
        return medicalStaffAppointmentID;
    }
    public String getAppointmentID() {
        return appointmentID;
    }
    public String getMedicalStaffID() {
        return medicalStaffID;
    }
    public void setMedicalStaffAppointmentID(String medicalStaffAppointmentID) {
        this.medicalStaffAppointmentID = medicalStaffAppointmentID;
    }
    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }
    public void setMedicalStaffID(String medicalStaffID) {
        this.medicalStaffID = medicalStaffID;
    }
    @Override
    public String toString() {
        return "MedicalStaffAppointment{" +
                "medicalStaffAppointmentID='" + medicalStaffAppointmentID + '\'' +
                ", appointmentID='" + appointmentID + '\'' +
                ", medicalStaffID='" + medicalStaffID + '\'' +
                '}';
    }
}
