package com.example.Hospital.Management.System.Repository.InFile;

import com.example.Hospital.Management.System.Model.MedicalStaffAppointment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("medicalStaffAppointmentInFile")
public class MedicalStaffAppointmentInFileRepository extends InFileRepository<MedicalStaffAppointment> {
    public MedicalStaffAppointmentInFileRepository(ObjectMapper mapper, @Value("${app.data.folder:data/}") String dataFolder) {
        super(mapper, dataFolder, "medicalstaffappointments.json");
    }

    @Override
    protected String getId(MedicalStaffAppointment msa) {
        return msa.getMedicalStaffAppointmentID();
    }

    @Override
    protected void setId(MedicalStaffAppointment msa, String id) {
        msa.setMedicalStaffAppointmentID(id);
    }
}