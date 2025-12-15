package com.example.Hospital.Management.System.Service;

import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import com.example.Hospital.Management.System.SearchCriteria.HospitalSearchCriteria;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.RepositoryFactory;
import com.example.Hospital.Management.System.Repository.RepositoryModeHolder;
import com.example.Hospital.Management.System.Repository.RepositoryMode;
import com.example.Hospital.Management.System.Utils.ReflectionSorter;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HospitalService extends BaseService<Hospital> {

    private final AbstractRepository<Hospital> hospitalRepository;
    private final RepositoryModeHolder modeHolder;

    public HospitalService(RepositoryFactory factory, RepositoryModeHolder modeHolder) {
        this.hospitalRepository = factory.createRepository(Hospital.class);
        this.modeHolder = modeHolder;
    }

    @Override
    public void save(Hospital entity) {
        hospitalRepository.save(entity);
    }

    @Override
    protected void delete(Hospital entity) {
        hospitalRepository.delete(entity);
    }

    @Override
    public Hospital findById(String id) {
        return hospitalRepository.findById(id);
    }

    @Override
    protected List<Hospital> findAll() {
        return findAll(Sort.by("id").ascending());
    }

    @Override
    protected List<Hospital> findAll(Sort sort) {
        return hospitalRepository.findAll(sort);
    }

    // Metodă specifică pentru filtrare + sortare
    public List<Hospital> findAllFiltered(String sortField, String sortDir, HospitalSearchCriteria criteria) {

        // Traducem 'hospitalID' în 'id' pentru baza de date JPA, dacă e cazul
        String dbSortField = "hospitalID".equals(sortField) ? "id" : sortField;

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(dbSortField).ascending() :
                Sort.by(dbSortField).descending();

        if (modeHolder.getMode() == RepositoryMode.MYSQL) {
            // Pentru MySQL, trimitem criteriile la Adaptor -> Specification
            return hospitalRepository.findAll(criteria, sort);
        } else {
            // Fallback pentru In-Memory (dacă mai e cazul): aducem tot, sortăm, apoi filtrăm
            List<Hospital> hospitals = hospitalRepository.findAll(sort);

            if (criteria != null) {
                if (criteria.getHospitalName() != null && !criteria.getHospitalName().isEmpty()) {
                    hospitals = hospitals.stream()
                            .filter(h -> h.getHospitalName().toLowerCase().contains(criteria.getHospitalName().toLowerCase()))
                            .collect(Collectors.toList());
                }
                if (criteria.getCity() != null && !criteria.getCity().isEmpty()) {
                    hospitals = hospitals.stream()
                            .filter(h -> h.getCity().toLowerCase().contains(criteria.getCity().toLowerCase()))
                            .collect(Collectors.toList());
                }
            }
            return hospitals;
        }
    }
}