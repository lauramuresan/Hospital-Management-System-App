package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DBDepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    Optional<DepartmentEntity> findByDepartmentNameAndHospitalId(String departmentName, Long hospitalId);
    boolean existsByDepartmentNameAndHospitalId(String departmentName, Long hospitalId);
}

