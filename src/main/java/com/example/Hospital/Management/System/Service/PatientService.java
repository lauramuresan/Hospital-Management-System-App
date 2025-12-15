package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Patient;
import com.example.Hospital.Management.System.SearchCriteria.PatientSearchCriteria;
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
public class PatientService extends BaseService<Patient>{

    private final AbstractRepository<Patient> patientRepository;
    private final RepositoryModeHolder modeHolder;

    public PatientService(RepositoryFactory factory, RepositoryModeHolder modeHolder) {
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
        return findAllFiltered("patientName", "asc", null);
    }

    // NOU: Metodă pentru sortare și filtrare
    public List<Patient> findAllFiltered(String sortField, String sortDir, PatientSearchCriteria criteria) {
        // Mapare câmpuri sortare (DTO -> DB)
        String dbSortField = sortField;
        if ("patientID".equals(sortField)) dbSortField = "id";
        if ("pacientEmail".equals(sortField)) dbSortField = "pacientEmail";
        if ("patientBirthDate".equals(sortField)) dbSortField = "patientBirthDate";

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(dbSortField).ascending() :
                Sort.by(dbSortField).descending();

        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return patientRepository.findAll(criteria, sort);
        } else {
            // In-Memory Fallback cu filtrare manuală
            List<Patient> patients = patientRepository.findAll();

            if (sort != null && sort.isSorted()) {
                ReflectionSorter.sortList(patients, Patient.class, sort);
            }

            if (criteria != null) {
                if (criteria.getPatientName() != null && !criteria.getPatientName().isEmpty()) {
                    patients = patients.stream().filter(p -> p.getPatientName().toLowerCase().contains(criteria.getPatientName().toLowerCase())).collect(Collectors.toList());
                }
                if (criteria.getPacientEmail() != null && !criteria.getPacientEmail().isEmpty()) {
                    patients = patients.stream().filter(p -> p.getPacientEmail().toLowerCase().contains(criteria.getPacientEmail().toLowerCase())).collect(Collectors.toList());
                }
                if (criteria.getPatientBirthDateFrom() != null) {
                    patients = patients.stream().filter(p -> !p.getPatientBirthDate().isBefore(criteria.getPatientBirthDateFrom())).collect(Collectors.toList());
                }
                if (criteria.getPatientBirthDateTo() != null) {
                    patients = patients.stream().filter(p -> !p.getPatientBirthDate().isAfter(criteria.getPatientBirthDateTo())).collect(Collectors.toList());
                }
            }
            return patients;
        }
    }
}