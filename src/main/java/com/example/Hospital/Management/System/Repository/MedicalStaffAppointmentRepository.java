package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.MedicalStaffAppointment;
import org.springframework.stereotype.Repository;
@Repository
public class MedicalStaffAppointmentRepository extends InMemoryRepository<MedicalStaffAppointment> {
    @Override
    protected String getId(MedicalStaffAppointment msa) {
        return msa.getMedicalStaffAppointmentID();
    }
    @Override
    protected void setId(MedicalStaffAppointment msa, String id) {
        msa.setMedicalStaffAppointmentID(id);
    }
}
