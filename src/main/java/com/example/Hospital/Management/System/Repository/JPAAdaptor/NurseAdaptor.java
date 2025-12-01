package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.NurseMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.NurseEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Nurse;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBNurseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NurseAdaptor implements AbstractRepository<Nurse> {

    private final DBNurseRepository jpaRepository;

    public NurseAdaptor(DBNurseRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Nurse domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Asistentul Medical");

        // 1. VALIDARE BUSINESS: Unicitatea Email-ului
        if (domain.getStaffID() == null || !isExistingEmail(domain)) {
            // Presupunând că existsByStaffEmail există în DBNurseRepository
            if (jpaRepository.existsByStaffEmail(domain.getStaffEmail())) {
                // ✅ MESAJ LIZIBIL PENTRU UTILIZATOR
                throw new RuntimeException("Adresa de email '" + domain.getStaffEmail() + "' este deja utilizată de un alt membru al personalului.");
            }
        }

        // 2. Validare FK (Department ID) ar trebui să fie aici.

        jpaRepository.save(NurseMapper.toEntity(domain));
    }

    private boolean isExistingEmail(Nurse domain) {
        if (domain.getStaffID() == null) return false;
        try {
            Long id = MapperUtils.parseLong(domain.getStaffID());
            // Presupunând că NurseEntity are getStaffEmail
            return jpaRepository.findById(id)
                    .map(NurseEntity::getStaffEmail)
                    .filter(email -> email.equals(domain.getStaffEmail()))
                    .isPresent();
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public void delete(Nurse domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Asistentul Medical");
        RepositoryValidationUtils.requireIdForDelete(domain.getStaffID(), "ID-ul asistentului");

        Long id = RepositoryValidationUtils.parseIdOrThrow(domain.getStaffID(), "ID-ul asistentului este invalid.");
        jpaRepository.deleteById(id);
    }

    @Override
    public Nurse findById(String id) {
        Long parsed = RepositoryValidationUtils.parseIdOrNull(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed).map(NurseMapper::toDomain).orElse(null);
    }

    @Override
    public List<Nurse> findAll() {
        return jpaRepository.findAll().stream().map(NurseMapper::toDomain).collect(Collectors.toList());
    }
}