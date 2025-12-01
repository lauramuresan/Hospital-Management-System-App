package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.DoctorMapper;
import com.example.Hospital.Management.System.Mapper.MapperUtils;
import com.example.Hospital.Management.System.Model.DBModel.DoctorEntity;
import com.example.Hospital.Management.System.Model.GeneralModel.Doctor;
import com.example.Hospital.Management.System.Repository.AbstractRepository;
import com.example.Hospital.Management.System.Repository.DBRepository.DBDoctorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoctorAdaptor implements AbstractRepository<Doctor> {

    private final DBDoctorRepository jpaRepository;

    public DoctorAdaptor(DBDoctorRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Doctor domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Doctorul");

        if (domain.getMedicalSpeciality() == null) {
            throw new RuntimeException("Specializarea medicală este obligatorie.");
        }

        // 1. VALIDARE BUSINESS: Unicitatea pe Email + Specializare
        if (domain.getStaffID() == null || !isExistingDoctorInSpeciality(domain)) {
            // Presupunând că existsByStaffEmailAndMedicalSpeciality primește Enum
            if (jpaRepository.existsByStaffEmailAndMedicalSpeciality(
                    domain.getStaffEmail(),
                    domain.getMedicalSpeciality() // Trimite Enum-ul
            )) {
                // ✅ MESAJ LIZIBIL PENTRU UTILIZATOR
                throw new RuntimeException("Doctorul cu email-ul '" + domain.getStaffEmail() +
                        "' este deja înregistrat pentru specializarea: " + domain.getMedicalSpeciality() +
                        ". Puteți adăuga același doctor, dar doar cu o altă specializare.");
            }
        }

        // 2. Validare FK (Department ID) ar trebui să fie aici.
        // RepositoryValidationUtils.parseIdOrThrow(domain.getDepartmentID(), "ID-ul Departamentului este invalid.");

        jpaRepository.save(DoctorMapper.toEntity(domain));
    }

    private boolean isExistingDoctorInSpeciality(Doctor domain) {
        if (domain.getStaffID() == null) return false;
        try {
            Long id = MapperUtils.parseLong(domain.getStaffID());
            // Verificăm dacă email-ul ȘI specializarea NU s-au schimbat la update.
            return jpaRepository.findById(id)
                    .filter(entity -> entity.getStaffEmail().equals(domain.getStaffEmail()) &&
                            entity.getMedicalSpeciality().equals(domain.getMedicalSpeciality()))
                    .isPresent();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void delete(Doctor domain) {
        RepositoryValidationUtils.requireDomainNonNull(domain, "Doctorul");
        RepositoryValidationUtils.requireIdForDelete(domain.getStaffID(), "ID-ul doctorului");

        Long id = RepositoryValidationUtils.parseIdOrThrow(domain.getStaffID(), "ID-ul doctorului este invalid.");
        jpaRepository.deleteById(id);
    }

    @Override
    public Doctor findById(String id) {
        Long parsed = RepositoryValidationUtils.parseIdOrNull(id);
        if (parsed == null) return null;
        return jpaRepository.findById(parsed).map(DoctorMapper::toDomain).orElse(null);
    }

    @Override
    public List<Doctor> findAll() {
        return jpaRepository.findAll().stream().map(DoctorMapper::toDomain).collect(Collectors.toList());
    }
}