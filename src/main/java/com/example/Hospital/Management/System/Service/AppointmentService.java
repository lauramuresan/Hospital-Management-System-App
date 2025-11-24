package com.example.Hospital.Management.System.Service;
import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService extends BaseService<Appointment>{

    private final AbstractRepository<Appointment> appointmentRepository;

    public AppointmentService(RepositoryFactory factory) {
        this.appointmentRepository = factory.createRepository(Appointment.class);
    }

    @Override
    protected void save(Appointment entity){
        appointmentRepository.save(entity);
    }
    protected void delete(Appointment entity){
        appointmentRepository.delete(entity);
    }
    protected Appointment findById(String id){
        return appointmentRepository.findById(id);
    }
    protected List<Appointment> findAll(){
        return appointmentRepository.findAll();
    }
}
