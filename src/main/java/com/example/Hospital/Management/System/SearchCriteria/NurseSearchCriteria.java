package com.example.Hospital.Management.System.SearchCriteria;

import com.example.Hospital.Management.System.Model.Enums.NurseLevelQualification;

public class NurseSearchCriteria {

    private String staffName;
    private String staffEmail;
    private NurseLevelQualification qualificationLevel;
    private String departmentID;

    public NurseSearchCriteria() {}

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public NurseLevelQualification getQualificationLevel() {
        return qualificationLevel;
    }

    public void setQualificationLevel(NurseLevelQualification qualificationLevel) {
        this.qualificationLevel = qualificationLevel;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }
}