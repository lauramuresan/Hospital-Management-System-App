package com.example.Hospital.Management.System.Repository.JPA;

import com.example.Hospital.Management.System.Model.DBModel.HospitalEntity;
import com.example.Hospital.Management.System.SearchCriteria.HospitalSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class HospitalSpecification {

    public static Specification<HospitalEntity> filterByCriteria(HospitalSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria != null) {
                // Filtrare după Nume (case-insensitive, partial match)
                if (criteria.getHospitalName() != null && !criteria.getHospitalName().isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("hospitalName")),
                            "%" + criteria.getHospitalName().toLowerCase() + "%"
                    ));
                }

                // Filtrare după Oraș (case-insensitive, partial match)
                if (criteria.getCity() != null && !criteria.getCity().isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("city")),
                            "%" + criteria.getCity().toLowerCase() + "%"
                    ));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}