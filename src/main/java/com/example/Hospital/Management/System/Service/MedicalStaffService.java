package com.example.Hospital.Management.System.Service;
import com.example.Hospital.Management.System.Model.MedicalStaff;
import com.example.Hospital.Management.System.Repository.MedicalStaffRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalStaffService {

    private final MedicalStaffRepository medicalStaffRepository;
    public MedicalStaffService(MedicalStaffRepository medicalStaffRepository) {
        this.medicalStaffRepository = medicalStaffRepository;
    }
    public void addStaff(MedicalStaff staff) {
        medicalStaffRepository.save(staff);
    }
    public void removeStaff(String staffId) {
        MedicalStaff staff = medicalStaffRepository.findById(staffId);
        if (staff != null) medicalStaffRepository.delete(staff);
    }
    public MedicalStaff getStaffById(String id) {
        return medicalStaffRepository.findById(id);
    }
    public List<MedicalStaff> getAllStaff() {
        return medicalStaffRepository.findAll();
    }
}
