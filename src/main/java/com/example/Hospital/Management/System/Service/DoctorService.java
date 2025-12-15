package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import com.example.Hospital.Management.System.SearchCriteria.DoctorSearchCriteria;
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
public class DoctorService extends BaseService<Doctor> {
    private final AbstractRepository<Doctor> doctorRepository;
    private final RepositoryModeHolder modeHolder;

    public DoctorService(RepositoryFactory factory, RepositoryModeHolder modeHolder) {
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
        return findAllFiltered("staffID", "asc", null);
    }

    public List<Doctor> findAllFiltered(String sortField, String sortDir, DoctorSearchCriteria criteria) {
        // Mapare câmpuri sortare
        String dbSortField = sortField;
        if ("staffID".equals(sortField)) dbSortField = "id";
        if ("departmentID".equals(sortField) || "department.id".equals(sortField)) dbSortField = "department.id";

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(dbSortField).ascending() :
                Sort.by(dbSortField).descending();

        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return doctorRepository.findAll(criteria, sort);
        } else {
            // In-Memory Fallback
            List<Doctor> doctors = doctorRepository.findAll();

            if (sort != null && sort.isSorted()) {
                if (!dbSortField.contains(".")) {
                    ReflectionSorter.sortList(doctors, Doctor.class, sort);
                }
            }

            if (criteria != null) {
                if (criteria.getStaffName() != null && !criteria.getStaffName().isEmpty()) {
                    doctors = doctors.stream().filter(d -> d.getStaffName().toLowerCase().contains(criteria.getStaffName().toLowerCase())).collect(Collectors.toList());
                }
                if (criteria.getMedicalSpeciality() != null) {
                    doctors = doctors.stream().filter(d -> d.getMedicalSpeciality() == criteria.getMedicalSpeciality()).collect(Collectors.toList());
                }
                if (criteria.getDepartmentID() != null && !criteria.getDepartmentID().isEmpty()) {
                    doctors = doctors.stream().filter(d -> d.getDepartmentID().equals(criteria.getDepartmentID())).collect(Collectors.toList());
                }
            }
            return doctors;
        }
    }
}