package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DBDepartmentRepository extends JpaRepository<DepartmentEntity, Long>, JpaSpecificationExecutor<DepartmentEntity> {

    Optional<DepartmentEntity> findByDepartmentNameAndHospitalId(String departmentName, Long hospitalId);
    boolean existsByDepartmentNameAndHospitalId(String departmentName, Long hospitalId);
}