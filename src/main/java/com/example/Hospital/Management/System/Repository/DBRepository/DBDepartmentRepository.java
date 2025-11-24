package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBDepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}