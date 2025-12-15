package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import com.example.Hospital.Management.System.SearchCriteria.HospitalSearchCriteria;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBHospitalRepository;
import com.example.Hospital.Management.System.Repository.JPA.HospitalSpecification;
import com.example.Hospital.Management.System.Mapper.HospitalMapper;
import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class HospitalAdaptor implements AbstractRepository<Hospital> {

    private final DBHospitalRepository repository;
    private final HospitalMapper mapper;

    public HospitalAdaptor(DBHospitalRepository repository, HospitalMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(Hospital hospital) {
        // CORECTAT: Apelează metoda toEntity pe instanța 'mapper'
        HospitalEntity entity = mapper.toEntity(hospital);
        repository.save(entity);
    }

    @Override
    public void delete(Hospital hospital) {
        if (hospital.getHospitalID() != null) {
            repository.deleteById(Long.valueOf(hospital.getHospitalID()));
        }
    }

    @Override
    public Hospital findById(String id) {
        Optional<HospitalEntity> entity = repository.findById(Long.valueOf(id));
        // CORECTAT: Apelează metoda toDomain pe instanța 'mapper'
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public List<Hospital> findAll() {
        // CORECTAT: Apelează metoda toDomain pe instanța 'mapper'
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Hospital> findAll(Sort sort) {
        // CORECTAT: Apelează metoda toDomain pe instanța 'mapper'
        return repository.findAll(sort).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Hospital> findAll(Object searchCriteria, Sort sort) {
        Specification<HospitalEntity> spec = null;

        if (searchCriteria instanceof HospitalSearchCriteria) {
            spec = HospitalSpecification.filterByCriteria((HospitalSearchCriteria) searchCriteria);
        }

        List<HospitalEntity> entities = repository.findAll(spec, sort);

        // CORECTAT: Apelează metoda toDomain pe instanța 'mapper'
        return entities.stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}