package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.NurseMapper;
import com.example.Hospital.Management.System.Model.GeneralModel.Nurse;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBNurseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NurseAdaptor implements AbstractRepository<Nurse> {

    private final DBNurseRepository jpaRepository;
    // Eliminat: private final NurseMapper mapper;

    // CORECȚIE: Eliminăm parametrul NurseMapper din constructor
    public NurseAdaptor(DBNurseRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
        // Eliminat: this.mapper = mapper;
    }

    @Override
    public void save(Nurse domain) {
        // CORECȚIE: Apelăm metoda statică direct pe clasa NurseMapper
        jpaRepository.save(NurseMapper.toEntity(domain));
    }

    @Override
    public void delete(Nurse domain) {
        if (domain.getStaffID() != null) {
            jpaRepository.deleteById(Long.valueOf(domain.getStaffID()));
        }
    }

    @Override
    public Nurse findById(String id) {
        try {
            // CORECȚIE: Folosim referința pe CLASĂ (NurseMapper::toDomain)
            return jpaRepository.findById(Long.valueOf(id)).map(NurseMapper::toDomain).orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Nurse> findAll() {
        // CORECȚIE: Folosim referința pe CLASĂ (NurseMapper::toDomain)
        return jpaRepository.findAll().stream().map(NurseMapper::toDomain).collect(Collectors.toList());
    }
}