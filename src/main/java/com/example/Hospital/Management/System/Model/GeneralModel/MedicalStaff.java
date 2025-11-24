package com.example.Hospital.Management.System.Model.GeneralModel;

import java.util.List;

public abstract class MedicalStaff {
    private String staffID;
    private String departmentID;
    private String staffName;
    private String staffEmail;
    private List<Appointment> appointments;
    public MedicalStaff() {
    }
    public MedicalStaff(String staffID, String departmentID, String staffName, List<Appointment> appointments, String staffEmail) {
        this.staffID = staffID;
        this.departmentID = departmentID;
        this.staffName = staffName;
        this.appointments = appointments;
        this.staffEmail = staffEmail;
    }
    public String getStaffID() {
        return staffID;
    }
    public String getDepartmentID() {
        return departmentID;
    }
    public String getStaffName() {
        return staffName;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public String getStaffEmail() {
        return staffEmail;
    }
    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }
    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    @Override
    public String toString() {
        return "MedicalStaff{" +
                "staffID='" + staffID + '\'' +
                ", departmentID='" + departmentID + '\'' +
                ", staffName='" + staffName + '\'' +
                ", staffEmail='" + staffEmail + '\'' +
                ", appointments=" + appointments +
                '}';
    }
}
