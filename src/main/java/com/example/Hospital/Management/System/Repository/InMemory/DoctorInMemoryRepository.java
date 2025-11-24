package com.example.Hospital.Management.System.Repository.InMemory;
import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import org.springframework.stereotype.Repository;
@Repository("doctorInMemory")
public class DoctorInMemoryRepository extends InMemoryRepository<Doctor> {
    @Override
    protected String getId(Doctor doctor) {
        return doctor.getStaffID();
    }
    @Override
    protected void setId(Doctor doctor, String id) {
        doctor.setStaffID(id);
    }
}
