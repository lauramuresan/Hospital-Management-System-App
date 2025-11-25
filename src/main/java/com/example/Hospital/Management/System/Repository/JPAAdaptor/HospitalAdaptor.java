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

    public HospitalAdaptor(DBHospitalRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Hospital domain) {
        jpaRepository.save(HospitalMapper.toEntity(domain));
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
            return jpaRepository.findById(Long.valueOf(id)).map(HospitalMapper::toDomain).orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Hospital> findAll() {
        return jpaRepository.findAll().stream().map(HospitalMapper::toDomain).collect(Collectors.toList());
    }
}