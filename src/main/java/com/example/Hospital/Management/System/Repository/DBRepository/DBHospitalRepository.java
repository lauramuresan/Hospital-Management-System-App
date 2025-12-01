package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBHospitalRepository extends JpaRepository<HospitalEntity, Long> {

    boolean existsByHospitalName(String hospitalName);
}