package com.example.Hospital.Management.System.Repository.JPA;

import com.example.Hospital.Management.System.Model.DBModel.PatientEntity;
import com.example.Hospital.Management.System.SearchCriteria.PatientSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class PatientSpecification {

    public static Specification<PatientEntity> filterByCriteria(PatientSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria != null) {
                // Filtrare după Nume (case-insensitive LIKE)
                if (criteria.getPatientName() != null && !criteria.getPatientName().isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("patientName")),
                            "%" + criteria.getPatientName().toLowerCase() + "%"
                    ));
                }

                // Filtrare după Email (case-insensitive LIKE)
                if (criteria.getPacientEmail() != null && !criteria.getPacientEmail().isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("pacientEmail")),
                            "%" + criteria.getPacientEmail().toLowerCase() + "%"
                    ));
                }

                // Filtrare Data Nașterii (De la)
                if (criteria.getPatientBirthDateFrom() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("patientBirthDate"), criteria.getPatientBirthDateFrom()));
                }

                // Filtrare Data Nașterii (Până la)
                if (criteria.getPatientBirthDateTo() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("patientBirthDate"), criteria.getPatientBirthDateTo()));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}