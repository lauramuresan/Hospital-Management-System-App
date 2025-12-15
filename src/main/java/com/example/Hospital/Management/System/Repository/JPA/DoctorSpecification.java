package com.example.Hospital.Management.System.Repository.JPA;

import com.example.Hospital.Management.System.Model.DBModel.DoctorEntity;
import com.example.Hospital.Management.System.SearchCriteria.DoctorSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class DoctorSpecification {

    public static Specification<DoctorEntity> filterByCriteria(DoctorSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria != null) {
                // Filtrare după Nume (partial, case-insensitive)
                if (criteria.getStaffName() != null && !criteria.getStaffName().isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("staffName")),
                            "%" + criteria.getStaffName().toLowerCase() + "%"
                    ));
                }

                // Filtrare după Email (partial, case-insensitive)
                if (criteria.getStaffEmail() != null && !criteria.getStaffEmail().isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("staffEmail")),
                            "%" + criteria.getStaffEmail().toLowerCase() + "%"
                    ));
                }

                // Filtrare după Specializare (Enum exact)
                if (criteria.getMedicalSpeciality() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("medicalSpeciality"), criteria.getMedicalSpeciality()));
                }

                // Filtrare după ID Departament
                if (criteria.getDepartmentID() != null && !criteria.getDepartmentID().isEmpty()) {
                    try {
                        Long deptId = Long.valueOf(criteria.getDepartmentID());
                        predicates.add(criteriaBuilder.equal(root.get("department").get("id"), deptId));
                    } catch (NumberFormatException ignored) {}
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}