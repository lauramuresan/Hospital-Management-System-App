package com.example.Hospital.Management.System.Repository.JPAAdaptor;

import com.example.Hospital.Management.System.Mapper.DoctorMapper;
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
    private final DoctorMapper mapper;

    public DoctorAdaptor(DBDoctorRepository jpaRepository, DoctorMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Doctor domain) {
        // Business Validation: Unicitatea LicenseNumber
        if (domain.getStaffID() == null || !isExistingLicense(domain)) {
            if (jpaRepository.existsByLicenseNumber(domain.getLicenseNumber())) {
                throw new RuntimeException("Numărul licenței " + domain.getLicenseNumber() + " este deja înregistrat.");
            }
        }

        jpaRepository.save(mapper.toEntity(domain));
    }

    // Metodă ajutătoare pentru a evita excepția la update dacă numărul licenței nu s-a schimbat
    private boolean isExistingLicense(Doctor domain) {
        if (domain.getStaffID() == null) return false;
        try {
            Long id = Long.valueOf(domain.getStaffID());
            return jpaRepository.findById(id)
                    .map(DoctorEntity::getLicenseNumber)
                    .filter(license -> license.equals(domain.getLicenseNumber()))
                    .isPresent();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void delete(Doctor domain) {
        if (domain.getStaffID() != null) {
            jpaRepository.deleteById(Long.valueOf(domain.getStaffID()));
        }
    }

    @Override
    public Doctor findById(String id) {
        try {
            return jpaRepository.findById(Long.valueOf(id)).map(mapper::toDomain).orElse(null);
        } catch (NumberFormatException e) { return null; }
    }

    @Override
    public List<Doctor> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}