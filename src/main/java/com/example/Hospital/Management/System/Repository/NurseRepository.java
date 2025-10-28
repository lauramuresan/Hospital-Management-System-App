package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.Nurse;
import org.springframework.stereotype.Repository;
@Repository
public class NurseRepository extends InMemoryRepository<Nurse> {
    @Override
    protected String getId(Nurse nurse) {
        return nurse.getStaffID();
    }
    @Override
    protected void setId(Nurse nurse, String id) {
        nurse.setStaffID(id);
    }
}
