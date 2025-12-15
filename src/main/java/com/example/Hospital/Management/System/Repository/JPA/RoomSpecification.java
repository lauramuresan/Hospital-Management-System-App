package com.example.Hospital.Management.System.Repository.JPA;

import com.example.Hospital.Management.System.Model.DBModel.RoomEntity;
import com.example.Hospital.Management.System.SearchCriteria.RoomSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class RoomSpecification {

    public static Specification<RoomEntity> filterByCriteria(RoomSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria != null) {
                // Filtrare după Număr (partial match)
                if (criteria.getNumber() != null && !criteria.getNumber().isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("number")),
                            "%" + criteria.getNumber().toLowerCase() + "%"
                    ));
                }

                // Filtrare după ID Spital
                if (criteria.getHospitalID() != null && !criteria.getHospitalID().isEmpty()) {
                    try {
                        Long hId = Long.valueOf(criteria.getHospitalID());
                        predicates.add(criteriaBuilder.equal(root.get("hospital").get("id"), hId));
                    } catch (NumberFormatException ignored) {}
                }

                // Filtrare după Status
                if (criteria.getStatus() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
                }

                // Filtrare după Capacitate (Range)
                if (criteria.getMinCapacity() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), criteria.getMinCapacity()));
                }
                if (criteria.getMaxCapacity() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("capacity"), criteria.getMaxCapacity()));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}