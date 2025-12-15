package com.example.Hospital.Management.System.Repository.JPA;

import com.example.Hospital.Management.System.Model.DBModel.DepartmentEntity;
import com.example.Hospital.Management.System.SearchCriteria.DepartmentSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class DepartmentSpecification {

    public static Specification<DepartmentEntity> filterByCriteria(DepartmentSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria != null) {
                // Filtrare după Nume Departament (case-insensitive, partial match)
                if (criteria.getDepartmentName() != null && !criteria.getDepartmentName().isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("departmentName")),
                            "%" + criteria.getDepartmentName().toLowerCase() + "%"
                    ));
                }

                // Filtrare după ID Spital
                if (criteria.getHospitalID() != null && !criteria.getHospitalID().isEmpty()) {
                    try {
                        Long hId = Long.valueOf(criteria.getHospitalID());
                        predicates.add(criteriaBuilder.equal(root.get("hospital").get("id"), hId));
                    } catch (NumberFormatException ignored) {}
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}