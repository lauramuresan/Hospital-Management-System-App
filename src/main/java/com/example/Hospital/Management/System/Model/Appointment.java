package com.example.Hospital.Management.System.Model;

import com.example.Hospital.Management.System.Model.Enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String departmentID;
    private LocalDateTime admissionDate; // schimbat din String
    private AppointmentStatus status;
    private List<MedicalStaff> medicalStaffList;

    public Appointment(String appointmentID, String patientID, String departmentID, LocalDateTime admissionDate, AppointmentStatus status, List<MedicalStaff> medicalStaffList) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.departmentID = departmentID;
        this.admissionDate = admissionDate;
        this.status = status;
        this.medicalStaffList = medicalStaffList;
    }

    public String getAppointmentID() { return appointmentID; }
    public String getPatientID() { return patientID; }
    public String getDepartmentID() { return departmentID; }
    public LocalDateTime getAdmissionDate() { return admissionDate; }
    public AppointmentStatus getStatus() { return status; }
    public List<MedicalStaff> getMedicalStaffList() { return medicalStaffList; }

    public void setAppointmentID(String appointmentID) { this.appointmentID = appointmentID; }
    public void setPatientID(String patientID) { this.patientID = patientID; }
    public void setDepartmentID(String departmentID) { this.departmentID = departmentID; }
    public void setAdmissionDate(LocalDateTime admissionDate) { this.admissionDate = admissionDate; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    public void setMedicalStaffList(List<MedicalStaff> medicalStaffList) { this.medicalStaffList = medicalStaffList; }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID='" + appointmentID + '\'' +
                ", patientID='" + patientID + '\'' +
                ", departmentID='" + departmentID + '\'' +
                ", admissionDate=" + admissionDate +
                ", status=" + status +
                ", medicalStaffList=" + medicalStaffList +
                '}';
    }
}
