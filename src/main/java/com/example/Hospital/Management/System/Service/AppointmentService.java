package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Appointment;
import com.example.Hospital.Management.System.SearchCriteria.AppointmentSearchCriteria;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import com.example.Hospital.Management.System.Repository.RepositoryMode;
import com.example.Hospital.Management.System.Repository.RepositoryModeHolder;
import com.example.Hospital.Management.System.Utils.ReflectionSorter;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentService extends BaseService<Appointment> {

    private final AbstractRepository<Appointment> appointmentRepository;
    private final RepositoryModeHolder modeHolder;

    public AppointmentService(RepositoryFactory factory, RepositoryModeHolder modeHolder) {
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
        return findAllFiltered("id", "asc", null);
    }

    public List<Appointment> findAllFiltered(String sortField, String sortDir, AppointmentSearchCriteria criteria) {
        // Mapare câmpuri sortare DTO -> DB
        String dbSortField = sortField;
        if ("patientID".equals(sortField) || "patient.id".equals(sortField)) dbSortField = "patient.id";
        if ("roomID".equals(sortField) || "room.id".equals(sortField)) dbSortField = "room.id";
        if ("appointmentID".equals(sortField)) dbSortField = "id";

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(dbSortField).ascending() :
                Sort.by(dbSortField).descending();

        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return appointmentRepository.findAll(criteria, sort);
        } else {
            // Logica In-Memory (Fallback)
            List<Appointment> appointments = appointmentRepository.findAll();

            if (sort != null && sort.isSorted()) {
                // Notă: ReflectionSorter poate avea probleme cu câmpuri imbricate gen patient.id
                // Pentru simplitate la In-Memory sortăm doar după câmpuri directe dacă e posibil
                if (!sortField.contains(".")) {
                    ReflectionSorter.sortList(appointments, Appointment.class, sort);
                }
            }

            if (criteria != null) {
                if (criteria.getPatientID() != null && !criteria.getPatientID().isEmpty()) {
                    appointments = appointments.stream().filter(a -> a.getPatientID().equals(criteria.getPatientID())).collect(Collectors.toList());
                }
                if (criteria.getRoomID() != null && !criteria.getRoomID().isEmpty()) {
                    appointments = appointments.stream().filter(a -> a.getRoomID().equals(criteria.getRoomID())).collect(Collectors.toList());
                }
                if (criteria.getStatus() != null) {
                    appointments = appointments.stream().filter(a -> a.getStatus() == criteria.getStatus()).collect(Collectors.toList());
                }
            }
            return appointments;
        }
    }
}