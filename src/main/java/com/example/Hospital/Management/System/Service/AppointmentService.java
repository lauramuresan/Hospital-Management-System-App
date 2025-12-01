package com.example.Hospital.Management.System.Service;
import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class AppointmentService extends BaseService<Appointment>{

    private final AbstractRepository<Appointment> appointmentRepository;

    public AppointmentService(RepositoryFactory factory) {
        this.appointmentRepository = factory.createRepository(Appointment.class);
    }

    @Override
    public void save(Appointment entity){
        appointmentRepository.save(entity);
    }
    @Override
    protected void delete(Appointment entity){
        appointmentRepository.delete(entity);
    }
    @Override
    protected Appointment findById(String id){
        return appointmentRepository.findById(id);
    }
    @Override
    protected List<Appointment> findAll(){
        return appointmentRepository.findAll();
    }
}