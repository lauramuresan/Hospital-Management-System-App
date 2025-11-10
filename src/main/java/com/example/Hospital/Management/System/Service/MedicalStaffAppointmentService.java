package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.Appointment;
import com.example.Hospital.Management.System.Model.MedicalStaffAppointment;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.InMemory.MedicalStaffAppointmentInMemoryRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalStaffAppointmentService extends BaseService<MedicalStaffAppointment>{
    private final AbstractRepository<MedicalStaffAppointment> medicalStaffAppointmentRepository;

    public MedicalStaffAppointmentService(RepositoryFactory factory) {
        this.medicalStaffAppointmentRepository = factory.createRepository(MedicalStaffAppointment.class);
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