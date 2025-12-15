package com.example.Hospital.Management.System.Repository.DBRepository;

import com.example.Hospital.Management.System.Model.DBModel.NurseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DBNurseRepository extends JpaRepository<NurseEntity, Long>, JpaSpecificationExecutor<NurseEntity> {

    boolean existsByStaffEmail(String staffEmail);
}