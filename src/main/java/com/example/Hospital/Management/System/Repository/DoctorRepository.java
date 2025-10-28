package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.Doctor;
import org.springframework.stereotype.Repository;
@Repository
public class DoctorRepository extends InMemoryRepository<Doctor> {
    @Override
    protected String getId(Doctor doctor) {
        return doctor.getStaffID();
    }
    @Override
    protected void setId(Doctor doctor, String id) {
        doctor.setStaffID(id);
    }
}
