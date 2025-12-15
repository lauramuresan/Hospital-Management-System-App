package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
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
public class PatientService extends BaseService<Patient>{

    private final AbstractRepository<Patient> patientRepository;
    private final RepositoryModeHolder modeHolder; // INJECTIE NOUA

    public PatientService(RepositoryFactory factory, RepositoryModeHolder modeHolder) { // MODIFICAT CONSTRUCTOR
        this.patientRepository = factory.createRepository(Patient.class);
        this.modeHolder = modeHolder;
    }

    @Override
    public void save(Patient entity){
        patientRepository.save(entity);
    }
    @Override
    protected void delete(Patient entity){
        patientRepository.delete(entity);
    }
    @Override
    public Patient findById(String id){
        return patientRepository.findById(id);
    }
    @Override
    public List<Patient> findAll(){
        return patientRepository.findAll();
    }

    @Override
    protected List<Patient> findAll(Sort sort) {
        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return patientRepository.findAll(sort);
        } else {
            List<Patient> patients = patientRepository.findAll();

            if (sort != null && sort.isSorted()) {
                ReflectionSorter.sortList(patients, Patient.class, sort);
            }
            return patients;
        }
    }
}