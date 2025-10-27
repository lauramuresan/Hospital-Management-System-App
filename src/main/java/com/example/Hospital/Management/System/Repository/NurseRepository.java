package com.example.Hospital.Management.System.Repository;
import com.example.Hospital.Management.System.Model.Nurse;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

@Repository
public class NurseRepository implements AbstractRepository<Nurse> {
    private final HashMap<String, Nurse> nurses = new HashMap<>();
    @Override
    public void save(Nurse nurse) {
        if (nurse.getStaffID() == null || nurse.getStaffID().isEmpty()) {
            nurse.setStaffID(UUID.randomUUID().toString());
        }
        nurses.put(nurse.getStaffID(), nurse);
    }
    public void delete(Nurse nurse) {
        nurses.remove(nurse.getStaffID());
    }
    public Nurse findById(String id){
        return nurses.get(id);
    }
    public List<Nurse> findAll(){
        return new ArrayList<>(nurses.values());
    }
}
