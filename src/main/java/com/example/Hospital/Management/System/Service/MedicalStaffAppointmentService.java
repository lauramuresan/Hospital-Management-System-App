package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaffAppointment;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Adăugat
import java.util.List;

@Service
@Transactional // Adăugat
public class MedicalStaffAppointmentService extends BaseService<MedicalStaffAppointment>{
    private final AbstractRepository<MedicalStaffAppointment> medicalStaffAppointmentRepository;

    public MedicalStaffAppointmentService(RepositoryFactory factory) {
        this.medicalStaffAppointmentRepository = factory.createRepository(MedicalStaffAppointment.class);
    }

    @Override
    public void save(MedicalStaffAppointment entity){
        medicalStaffAppointmentRepository.save(entity);
    }
    @Override
    protected void delete(MedicalStaffAppointment entity){ // Adăugat @Override
        medicalStaffAppointmentRepository.delete(entity);
    }
    @Override
    protected MedicalStaffAppointment findById(String id){ // Adăugat @Override
        return medicalStaffAppointmentRepository.findById(id);
    }
    @Override
    protected List<MedicalStaffAppointment> findAll(){ // Adăugat @Override
        return medicalStaffAppointmentRepository.findAll();
    }
}