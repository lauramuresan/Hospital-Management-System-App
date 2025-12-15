package com.example.Hospital.Management.System.SearchCriteria;

import com.example.Hospital.Management.System.Model.Enums.RoomAvailability;

public class RoomSearchCriteria {

    private String number;
    private String hospitalID;
    private Integer minCapacity;
    private Integer maxCapacity;
    private RoomAvailability status;

    public RoomSearchCriteria() {}

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getHospitalID() { return hospitalID; }
    public void setHospitalID(String hospitalID) { this.hospitalID = hospitalID; }

    public Integer getMinCapacity() { return minCapacity; }
    public void setMinCapacity(Integer minCapacity) { this.minCapacity = minCapacity; }

    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }

    public RoomAvailability getStatus() { return status; }
    public void setStatus(RoomAvailability status) { this.status = status; }
}