package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.MedicalStaff;
import org.springframework.stereotype.Repository;
@Repository
public class MedicalStaffRepository extends InMemoryRepository<MedicalStaff> {
    @Override
    protected String getId(MedicalStaff medicalStaff) {
        return medicalStaff.getStaffID();
    }
    @Override
    protected void setId(MedicalStaff medicalStaff, String id) {
        medicalStaff.setStaffID(id);
    }
}
