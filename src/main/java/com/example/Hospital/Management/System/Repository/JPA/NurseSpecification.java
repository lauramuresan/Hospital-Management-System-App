package com.example.Hospital.Management.System.Repository.JPA;

import com.example.Hospital.Management.System.Model.DBModel.NurseEntity;
import com.example.Hospital.Management.System.SearchCriteria.NurseSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class NurseSpecification {

    public static Specification<NurseEntity> filterByCriteria(NurseSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria != null) {
                // Filtrare după Nume
                if (criteria.getStaffName() != null && !criteria.getStaffName().isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("staffName")),
                            "%" + criteria.getStaffName().toLowerCase() + "%"
                    ));
                }

                // Filtrare după Email
                if (criteria.getStaffEmail() != null && !criteria.getStaffEmail().isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("staffEmail")),
                            "%" + criteria.getStaffEmail().toLowerCase() + "%"
                    ));
                }

                // Filtrare după Calificare (nurseCategory în DB)
                if (criteria.getQualificationLevel() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("nurseCategory"), criteria.getQualificationLevel()));
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