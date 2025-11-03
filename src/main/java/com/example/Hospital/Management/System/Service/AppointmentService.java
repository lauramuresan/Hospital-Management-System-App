package com.example.Hospital.Management.System.Service;
import com.example.Hospital.Management.System.Model.Appointment;
import com.example.Hospital.Management.System.Repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService extends BaseService<Appointment>{

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
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
