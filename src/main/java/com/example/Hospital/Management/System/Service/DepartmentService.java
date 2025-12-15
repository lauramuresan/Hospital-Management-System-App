package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Department;
import com.example.Hospital.Management.System.SearchCriteria.DepartmentSearchCriteria;
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
public class DepartmentService extends BaseService<Department> {

    private final AbstractRepository<Department> departmentRepository;
    private final RepositoryModeHolder modeHolder;

    public DepartmentService(RepositoryFactory factory, RepositoryModeHolder modeHolder) {
        this.departmentRepository = factory.createRepository(Department.class);
        this.modeHolder = modeHolder;
    }

    @Override
    public void save(Department entity){
        departmentRepository.save(entity);
    }
    @Override
    protected void delete(Department entity){
        departmentRepository.delete(entity);
    }
    @Override
    public Department findById(String id){
        return departmentRepository.findById(id);
    }
    @Override
    protected List<Department> findAll(){
        return departmentRepository.findAll();
    }

    @Override
    protected List<Department> findAll(Sort sort) {
        return findAllFiltered("departmentID", "asc", null);
    }

    public List<Department> findAllFiltered(String sortField, String sortDir, DepartmentSearchCriteria criteria) {
        // Mapare câmpuri sortare
        String dbSortField = sortField;
        if ("departmentID".equals(sortField)) dbSortField = "id";
        if ("hospitalID".equals(sortField) || "hospital.id".equals(sortField)) dbSortField = "hospital.id";

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(dbSortField).ascending() :
                Sort.by(dbSortField).descending();

        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            return departmentRepository.findAll(criteria, sort);
        } else {
            // In-Memory Fallback
            List<Department> departments = departmentRepository.findAll();

            if (sort != null && sort.isSorted()) {
                if (!dbSortField.contains(".")) {
                    ReflectionSorter.sortList(departments, Department.class, sort);
                }
            }

            if (criteria != null) {
                if (criteria.getDepartmentName() != null && !criteria.getDepartmentName().isEmpty()) {
                    departments = departments.stream()
                            .filter(d -> d.getDepartmentName().toLowerCase().contains(criteria.getDepartmentName().toLowerCase()))
                            .collect(Collectors.toList());
                }
                if (criteria.getHospitalID() != null && !criteria.getHospitalID().isEmpty()) {
                    departments = departments.stream()
                            .filter(d -> d.getHospitalID().equals(criteria.getHospitalID()))
                            .collect(Collectors.toList());
                }
            }
            return departments;
        }
    }
}