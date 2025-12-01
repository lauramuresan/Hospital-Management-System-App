package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.HospitalMapper;
import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;
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
        RepositoryValidationUtils.requireDomainNonNull(domain, "Spitalul");

        // 1. Unicitatea Numelui Spitalului
        if (domain.getHospitalID() == null || !isExistingHospitalName(domain)) {
            if (jpaRepository.existsByHospitalName(domain.getHospitalName())) {
                throw new RuntimeException("Numele spitalului '" + domain.getHospitalName() + "' există deja. Fiecare spital trebuie să aibă un nume unic.");
            }
        }

        jpaRepository.save(HospitalMapper.toEntity(domain));
    }

    private boolean isExistingHospitalName(Hospital domain) {
        if (domain.getHospitalID() == null) return false;
        try {
            Long id = RepositoryValidationUtils.parseIdOrNull(domain.getHospitalID());
            if (id == null) return false;
            return jpaRepository.findById(id)
                    .map(HospitalEntity::getHospitalName)
                    .filter(name -> name.equals(domain.getHospitalName()))
                    .isPresent();
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public void delete(Hospital domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Spitalul");
        RepositoryValidationUtils.requireIdForDelete(domain.getHospitalID(), "ID-ul spitalului");

        Long id = RepositoryValidationUtils.parseIdOrThrow(domain.getHospitalID(), "ID-ul spitalului este invalid.");
        jpaRepository.deleteById(id);
    }

    @Override
    public Hospital findById(String id) {
        Long parsed = RepositoryValidationUtils.parseIdOrNull(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed).map(HospitalMapper::toDomain).orElse(null);
    }

    @Override
    public List<Hospital> findAll() {
        return jpaRepository.findAll().stream().map(HospitalMapper::toDomain).collect(Collectors.toList());
    }
}