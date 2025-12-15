package com.example.Hospital.Management.System.SearchCriteria;

import java.time.LocalDate;

public class PatientSearchCriteria {

    private String patientName;
    private String pacientEmail;
    private LocalDate patientBirthDateFrom; // Căutare după interval de dată
    private LocalDate patientBirthDateTo;

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getPacientEmail() { return pacientEmail; }
    public void setPacientEmail(String pacientEmail) { this.pacientEmail = pacientEmail; }

    public LocalDate getPatientBirthDateFrom() { return patientBirthDateFrom; }
    public void setPatientBirthDateFrom(LocalDate patientBirthDateFrom) { this.patientBirthDateFrom = patientBirthDateFrom; }

    public LocalDate getPatientBirthDateTo() { return patientBirthDateTo; }
    public void setPatientBirthDateTo(LocalDate patientBirthDateTo) { this.patientBirthDateTo = patientBirthDateTo; }
}