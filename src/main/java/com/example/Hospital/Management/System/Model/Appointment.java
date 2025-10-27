package com.example.Hospital.Management.System.Model;

import java.util.List;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String departmentID;
    private String admissionDate;
    private String status;
    private List<MedicalStaff> medicalStaffList;

    public Appointment(String appointmentID, String patientID, String departmentID, String admissionDate, String status, List<MedicalStaff> medicalStaffList) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.departmentID = departmentID;
        this.admissionDate = admissionDate;
        this.status = status;
        this.medicalStaffList = medicalStaffList;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public String getStatus() {
        return status;
    }

    public List<MedicalStaff> getMedicalStaffList() {
        return medicalStaffList;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMedicalStaffList(List<MedicalStaff> medicalStaffList) {
        this.medicalStaffList = medicalStaffList;
    }
}
