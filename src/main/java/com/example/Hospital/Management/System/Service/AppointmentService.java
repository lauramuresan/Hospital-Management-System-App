package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import com.example.Hospital.Management.System.Repository.RepositoryMode; // IMPORT NOU
import com.example.Hospital.Management.System.Repository.RepositoryModeHolder; // IMPORT NOU
import com.example.Hospital.Management.System.Utils.ReflectionSorter; // IMPORT NOU

import org.springframework.data.domain.Sort; // IMPORT NOU
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class AppointmentService extends BaseService<Appointment>{

    private final AbstractRepository<Appointment> appointmentRepository;
    private final RepositoryModeHolder modeHolder; // INJECTIE NOUA

    public AppointmentService(RepositoryFactory factory, RepositoryModeHolder modeHolder) { // MODIFICAT CONSTRUCTOR
        this.appointmentRepository = factory.createRepository(Appointment.class);
        this.modeHolder = modeHolder;
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
    public Appointment findById(String id){
        return appointmentRepository.findById(id);
    }
    @Override
    public List<Appointment> findAll(){
        return appointmentRepository.findAll();
    }

    @Override
    protected List<Appointment> findAll(Sort sort) {
        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return appointmentRepository.findAll(sort);
        } else {
            List<Appointment> appointments = appointmentRepository.findAll();

            if (sort != null && sort.isSorted()) {
                ReflectionSorter.sortList(appointments, Appointment.class, sort);
            }
            return appointments;
        }
    }
}