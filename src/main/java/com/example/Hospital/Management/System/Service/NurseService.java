package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Nurse;
import com.example.Hospital.Management.System.SearchCriteria.NurseSearchCriteria;
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
public class NurseService extends BaseService<Nurse> {
    private final AbstractRepository<Nurse> nurseRepository;
    private final RepositoryModeHolder modeHolder;

    public NurseService(RepositoryFactory factory, RepositoryModeHolder modeHolder) {
        this.nurseRepository = factory.createRepository(Nurse.class);
        this.modeHolder = modeHolder;
    }

    @Override
    public void save(Nurse entity){
        nurseRepository.save(entity);
    }
    @Override
    protected void delete(Nurse entity){
        nurseRepository.delete(entity);
    }
    @Override
    public Nurse findById(String id){
        return nurseRepository.findById(id);
    }
    @Override
    public List<Nurse> findAll(){
        return nurseRepository.findAll();
    }

    @Override
    protected List<Nurse> findAll(Sort sort) {
        return findAllFiltered("staffID", "asc", null);
    }

    public List<Nurse> findAllFiltered(String sortField, String sortDir, NurseSearchCriteria criteria) {
        // Mapare câmpuri sortare
        String dbSortField = sortField;
        if ("staffID".equals(sortField)) dbSortField = "id";
        if ("qualificationLevel".equals(sortField)) dbSortField = "nurseCategory"; // Mapare specială DTO -> DB
        if ("departmentID".equals(sortField) || "department.id".equals(sortField)) dbSortField = "department.id";

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(dbSortField).ascending() :
                Sort.by(dbSortField).descending();

        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return nurseRepository.findAll(criteria, sort);
        } else {
            // In-Memory Fallback
            List<Nurse> nurses = nurseRepository.findAll();

            if (sort != null && sort.isSorted()) {
                if (!dbSortField.contains(".")) {
                    ReflectionSorter.sortList(nurses, Nurse.class, sort);
                }
            }

            if (criteria != null) {
                if (criteria.getStaffName() != null && !criteria.getStaffName().isEmpty()) {
                    nurses = nurses.stream().filter(n -> n.getStaffName().toLowerCase().contains(criteria.getStaffName().toLowerCase())).collect(Collectors.toList());
                }
                if (criteria.getQualificationLevel() != null) {
                    nurses = nurses.stream().filter(n -> n.getQualificationLevel() == criteria.getQualificationLevel()).collect(Collectors.toList());
                }
                if (criteria.getDepartmentID() != null && !criteria.getDepartmentID().isEmpty()) {
                    nurses = nurses.stream().filter(n -> n.getDepartmentID().equals(criteria.getDepartmentID())).collect(Collectors.toList());
                }
            }
            return nurses;
        }
    }
}