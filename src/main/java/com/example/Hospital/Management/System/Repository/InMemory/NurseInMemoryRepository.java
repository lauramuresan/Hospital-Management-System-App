package com.example.Hospital.Management.System.Repository.InMemory;
import com.example.Hospital.Management.System.Model.GeneralModel.Nurse;
import org.springframework.stereotype.Repository;
@Repository("nurseInMemory")
public class NurseInMemoryRepository extends InMemoryRepository<Nurse> {
    @Override
    protected String getId(Nurse nurse) {
        return nurse.getStaffID();
    }
    @Override
    protected void setId(Nurse nurse, String id) {
        nurse.setStaffID(id);
    }
}
