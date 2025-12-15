package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.MedicalStaffAppointment;
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
public class MedicalStaffAppointmentService extends BaseService<MedicalStaffAppointment>{
    private final AbstractRepository<MedicalStaffAppointment> medicalStaffAppointmentRepository;
    private final RepositoryModeHolder modeHolder; // INJECTIE NOUA

    public MedicalStaffAppointmentService(RepositoryFactory factory, RepositoryModeHolder modeHolder) { // MODIFICAT CONSTRUCTOR
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
    protected List<MedicalStaffAppointment> findAll(){
        return medicalStaffAppointmentRepository.findAll();
    }

    @Override
    protected List<MedicalStaffAppointment> findAll(Sort sort) {
        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return medicalStaffAppointmentRepository.findAll(sort);
        } else {
            List<MedicalStaffAppointment> assignments = medicalStaffAppointmentRepository.findAll();

            if (sort != null && sort.isSorted()) {
                ReflectionSorter.sortList(assignments, MedicalStaffAppointment.class, sort);
            }
            return assignments;
        }
    }
}