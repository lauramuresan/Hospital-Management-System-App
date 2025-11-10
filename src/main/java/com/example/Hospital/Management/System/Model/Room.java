package com.example.Hospital.Management.System.Model;

import com.example.Hospital.Management.System.Model.Enums.RoomAvailability;

import java.util.List;

public class Room {
    private String roomID;
    private String hospitalID;
    private double capacity;
    private String number;
    private RoomAvailability status;
    private List<Appointment> appointments;

    public Room(String roomID, String hospitalID, double capacity, String number, RoomAvailability status, List<Appointment> appointments) {
        this.roomID = roomID;
        this.hospitalID = hospitalID;
        this.capacity = capacity;
        this.number = number;
        this.status = status;
        this.appointments = appointments;
    }
    public String getRoomID() {
        return roomID;
    }
    public String getHospitalID() {
        return hospitalID;
    }
    public double getCapacity() {
        return capacity;
    }
    public String getNumber() {
        return number;
    }
    public RoomAvailability getStatus() {
        return status;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }
    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setStatus(RoomAvailability status) {
        this.status = status;
    }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID='" + roomID + '\'' +
                ", hospitalID='" + hospitalID + '\'' +
                ", capacity=" + capacity +
                ", number='" + number + '\'' +
                ", status='" + status + '\'' +
                ", appointments=" + appointments +
                '}';
    }
}
