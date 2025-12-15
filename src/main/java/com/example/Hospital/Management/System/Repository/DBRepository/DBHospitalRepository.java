package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DBHospitalRepository extends JpaRepository<HospitalEntity, Long>, JpaSpecificationExecutor<HospitalEntity> {
    // JpaSpecificationExecutor adaugă automat metodele findAll(Specification, Pageable/Sort)

    boolean existsByHospitalName(String hospitalName);
}