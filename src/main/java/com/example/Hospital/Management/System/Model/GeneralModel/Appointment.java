package com.example.Hospital.Management.System.Model.GeneralModel;

import com.example.Hospital.Management.System.Model.Enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String departmentID;
    private String roomID;
    private LocalDateTime admissionDate;
    private AppointmentStatus status;
    private List<MedicalStaff> medicalStaffList;

    public Appointment(String appointmentID, String patientID, String departmentID, String roomID, LocalDateTime admissionDate, AppointmentStatus status, List<MedicalStaff> medicalStaffList) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.departmentID = departmentID;
        this.roomID = roomID;
        this.admissionDate = admissionDate;
        this.status = status;
        this.medicalStaffList = medicalStaffList;
    }

    public Appointment() {}

    public String getAppointmentID() { return appointmentID; }
    public String getPatientID() { return patientID; }
    public String getDepartmentID() { return departmentID; }
    public String getRoomID() { return roomID; }
    public LocalDateTime getAdmissionDate() { return admissionDate; }
    public AppointmentStatus getStatus() { return status; }
    public List<MedicalStaff> getMedicalStaffList() { return medicalStaffList; }

    public void setAppointmentID(String appointmentID) { this.appointmentID = appointmentID; }
    public void setPatientID(String patientID) { this.patientID = patientID; }
    public void setDepartmentID(String departmentID) { this.departmentID = departmentID; }
    public void setRoomID(String roomID) { this.roomID = roomID; }
    public void setAdmissionDate(LocalDateTime admissionDate) { this.admissionDate = admissionDate; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    public void setMedicalStaffList(List<MedicalStaff> medicalStaffList) { this.medicalStaffList = medicalStaffList; }

    @Override
    public String toString() {
        if (status == null) {
            return "ID=" + appointmentID;
        }
        return "Appointment{" +
                "ID='" + appointmentID + '\'' +
                ", Patient='" + patientID + '\'' +
                ", Room='" + roomID + '\'' +
                ", Date=" + admissionDate +
                ", Status=" + status +
                ", Staff=" + medicalStaffList +
                '}';
    }
}