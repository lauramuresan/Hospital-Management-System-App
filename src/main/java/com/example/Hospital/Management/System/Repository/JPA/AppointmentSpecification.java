package com.example.Hospital.Management.System.Repository.JPA;

import com.example.Hospital.Management.System.Model.DBModel.AppointmentEntity;
import com.example.Hospital.Management.System.SearchCriteria.AppointmentSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class AppointmentSpecification {

    public static Specification<AppointmentEntity> filterByCriteria(AppointmentSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria != null) {
                // Filtrare după ID Pacient (convertim String în Long pentru DB)
                if (criteria.getPatientID() != null && !criteria.getPatientID().isEmpty()) {
                    try {
                        Long pId = Long.valueOf(criteria.getPatientID());
                        predicates.add(criteriaBuilder.equal(root.get("patient").get("id"), pId));
                    } catch (NumberFormatException ignored) {}
                }

                // Filtrare după ID Cameră
                if (criteria.getRoomID() != null && !criteria.getRoomID().isEmpty()) {
                    try {
                        Long rId = Long.valueOf(criteria.getRoomID());
                        predicates.add(criteriaBuilder.equal(root.get("room").get("id"), rId));
                    } catch (NumberFormatException ignored) {}
                }

                // Filtrare după Status
                if (criteria.getStatus() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
                }

                // Filtrare interval dată (Admission Date)
                if (criteria.getDateFrom() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("admissionDate"), criteria.getDateFrom()));
                }
                if (criteria.getDateTo() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("admissionDate"), criteria.getDateTo()));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}