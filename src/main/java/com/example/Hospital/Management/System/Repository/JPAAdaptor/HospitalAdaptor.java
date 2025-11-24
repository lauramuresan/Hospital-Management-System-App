package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.HospitalMapper;
import com.example.Hospital.Management.System.Model.GeneralModel.Hospital;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBHospitalRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HospitalAdaptor implements AbstractRepository<Hospital> {

    private final DBHospitalRepository jpaRepository;
    private final HospitalMapper mapper;

    public HospitalAdaptor(DBHospitalRepository jpaRepository, HospitalMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Hospital domain) {
        // Nu există o validare unică simplă impusă de Entitate, bazată pe JPA.
        jpaRepository.save(mapper.toEntity(domain));
    }

    @Override
    public void delete(Hospital domain) {
        if (domain.getHospitalID() != null) {
            jpaRepository.deleteById(Long.valueOf(domain.getHospitalID()));
        }
    }

    @Override
    public Hospital findById(String id) {
        try {
            return jpaRepository.findById(Long.valueOf(id)).map(mapper::toDomain).orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Hospital> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}