package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.MedicalStaffAppointment;
import com.example.Hospital.Management.System.Repository.InMemory.MedicalStaffAppointmentInMemoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalStaffAppointmentService extends BaseService<MedicalStaffAppointment>{
    private final MedicalStaffAppointmentInMemoryRepository medicalStaffAppointmentRepository;
    public MedicalStaffAppointmentService(MedicalStaffAppointmentInMemoryRepository medicalStaffAppointmentRepository) {
        this.medicalStaffAppointmentRepository = medicalStaffAppointmentRepository;
    }
    @Override
    protected void save(MedicalStaffAppointment entity){
        medicalStaffAppointmentRepository.save(entity);
    }
    protected void delete(MedicalStaffAppointment entity){
        medicalStaffAppointmentRepository.delete(entity);
    }
    protected MedicalStaffAppointment findById(String id){
        return medicalStaffAppointmentRepository.findById(id);
    }
    protected List<MedicalStaffAppointment> findAll(){
        return medicalStaffAppointmentRepository.findAll();
    }
}