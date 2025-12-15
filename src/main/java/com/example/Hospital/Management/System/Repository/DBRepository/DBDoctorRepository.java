package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.DoctorEntity;
import com.example.Hospital.Management.System.Model.Enums.MedicalSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DBDoctorRepository extends JpaRepository<DoctorEntity, Long>, JpaSpecificationExecutor<DoctorEntity> {

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByStaffEmail(String staffEmail);
    boolean existsByStaffEmailAndMedicalSpeciality(String staffEmail, MedicalSpecialty medicalSpeciality);
}