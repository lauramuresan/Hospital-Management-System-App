package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
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
public class DoctorService extends BaseService<Doctor>{
    private final AbstractRepository<Doctor> doctorRepository;
    private final RepositoryModeHolder modeHolder; // INJECTIE NOUA

    public DoctorService(RepositoryFactory factory, RepositoryModeHolder modeHolder) { // MODIFICAT CONSTRUCTOR
        this.doctorRepository = factory.createRepository(Doctor.class);
        this.modeHolder = modeHolder;
    }

    @Override
    public void save(Doctor entity){
        doctorRepository.save(entity);
    }
    @Override
    protected void delete(Doctor entity){
        doctorRepository.delete(entity);
    }
    @Override
    public Doctor findById(String id){
        return doctorRepository.findById(id);
    }
    @Override
    public List<Doctor> findAll(){
        return doctorRepository.findAll();
    }

    @Override
    protected List<Doctor> findAll(Sort sort) {
        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return doctorRepository.findAll(sort);
        } else {
            List<Doctor> doctors = doctorRepository.findAll();

            if (sort != null && sort.isSorted()) {
                ReflectionSorter.sortList(doctors, Doctor.class, sort);
            }
            return doctors;
        }
    }
}