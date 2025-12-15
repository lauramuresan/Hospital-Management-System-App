package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaffAppointment;
import com.example.Hospital.Management.System.SearchCriteria.MedicalStaffAppointmentSearchCriteria;
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
public class MedicalStaffAppointmentService extends BaseService<MedicalStaffAppointment>{
    private final AbstractRepository<MedicalStaffAppointment> medicalStaffAppointmentRepository;
    private final RepositoryModeHolder modeHolder;

    public MedicalStaffAppointmentService(RepositoryFactory factory, RepositoryModeHolder modeHolder) {
        this.medicalStaffAppointmentRepository = factory.createRepository(MedicalStaffAppointment.class);
        this.modeHolder = modeHolder;
    }

    @Override
    public void save(MedicalStaffAppointment entity){
        medicalStaffAppointmentRepository.save(entity);
    }
    @Override
    protected void delete(MedicalStaffAppointment entity){
        medicalStaffAppointmentRepository.delete(entity);
    }
    @Override
    public MedicalStaffAppointment findById(String id){
        return medicalStaffAppointmentRepository.findById(id);
    }
    @Override
    public List<MedicalStaffAppointment> findAll(){
        return medicalStaffAppointmentRepository.findAll();
    }

    @Override
    protected List<MedicalStaffAppointment> findAll(Sort sort) {
        return findAllFiltered("medicalStaffAppointmentID", "asc", null);
    }

    public List<MedicalStaffAppointment> findAllFiltered(String sortField, String sortDir, MedicalStaffAppointmentSearchCriteria criteria) {
        // Mapare câmpuri sortare
        String dbSortField = sortField;
        if ("medicalStaffAppointmentID".equals(sortField)) dbSortField = "id";
        if ("appointmentID".equals(sortField) || "appointment.id".equals(sortField)) dbSortField = "appointment.id";
        // Sortarea după medicalStaffID e complexă în DB (două coloane), deci e mai sigur să sortăm după ID-ul relației sau appointment

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(dbSortField).ascending() :
                Sort.by(dbSortField).descending();

        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return medicalStaffAppointmentRepository.findAll(criteria, sort);
        } else {
            // In-Memory Fallback
            List<MedicalStaffAppointment> list = medicalStaffAppointmentRepository.findAll();

            if (sort != null && sort.isSorted()) {
                if (!dbSortField.contains(".")) {
                    ReflectionSorter.sortList(list, MedicalStaffAppointment.class, sort);
                }
            }

            if (criteria != null) {
                if (criteria.getAppointmentID() != null && !criteria.getAppointmentID().isEmpty()) {
                    list = list.stream().filter(a -> a.getAppointmentID().equals(criteria.getAppointmentID())).collect(Collectors.toList());
                }
                if (criteria.getMedicalStaffID() != null && !criteria.getMedicalStaffID().isEmpty()) {
                    list = list.stream().filter(a -> a.getMedicalStaffID().equals(criteria.getMedicalStaffID())).collect(Collectors.toList());
                }
            }
            return list;
        }
    }
}