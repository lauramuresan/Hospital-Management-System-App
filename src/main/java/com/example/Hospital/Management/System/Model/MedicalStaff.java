package com.example.Hospital.Management.System.Model;

public abstract class MedicalStaff {
    private String staffID;
    private String departmentID;
    private String staffName;
    List<Appointment> appointments;

    public MedicalStaff(String staffID, String departmentID, String staffName, List<Appointment> appointments) {
        this.staffID = staffID;
        this.departmentID = departmentID;
        this.staffName = staffName;
        this.appointments = appointments;
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
}
