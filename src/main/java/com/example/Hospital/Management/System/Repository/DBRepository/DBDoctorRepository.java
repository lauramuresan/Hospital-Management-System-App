package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBDoctorRepository extends JpaRepository<DoctorEntity, Long> {

    // Metodă standard JpaRepository pentru a verifica existența după LicenseNumber.
    boolean existsByLicenseNumber(String licenseNumber);
}