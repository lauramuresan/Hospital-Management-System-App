package com.example.Hospital.Management.System.Service;
import com.example.Hospital.Management.System.Model.Department;
import com.example.Hospital.Management.System.Model.MedicalStaff;
import com.example.Hospital.Management.System.Repository.MedicalStaffRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalStaffService extends BaseService<MedicalStaff>{

    private final MedicalStaffRepository medicalStaffRepository;
    public MedicalStaffService(MedicalStaffRepository medicalStaffRepository) {
        this.medicalStaffRepository = medicalStaffRepository;
    }
    @Override
    protected void save(MedicalStaff entity){
        medicalStaffRepository.save(entity);
    }
    protected void delete(MedicalStaff entity){
        medicalStaffRepository.delete(entity);
    }
    protected MedicalStaff findById(String id){
        return medicalStaffRepository.findById(id);
    }
    protected List<MedicalStaff> findAll(){
        return medicalStaffRepository.findAll();
    }
}
