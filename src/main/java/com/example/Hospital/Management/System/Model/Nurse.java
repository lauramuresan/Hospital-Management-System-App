package com.example.Hospital.Management.System.Model;


import java.util.List;

public class Nurse extends MedicalStaff{
    private NurseLevelQualification qualificationLevel;
    public Nurse(String staffID, String departmentID, String staffName, List<Appointment> appointments,String staffEmail,NurseLevelQualification qualificationLevel) {
        super(staffID, departmentID, staffName, appointments,staffEmail);
        this.qualificationLevel = qualificationLevel;
    }
    public NurseLevelQualification getQualificationLevel() {
        return qualificationLevel;
    }
    public void setQualificationLevel(NurseLevelQualification qualificationLevel) {
        this.qualificationLevel = qualificationLevel;
    }
    @Override
    public String toString() {
        return "Nurse{" +
                "qualificationLevel='" + qualificationLevel + '\'' +
                ", appointments=" + appointments +
                '}';
    }
}
