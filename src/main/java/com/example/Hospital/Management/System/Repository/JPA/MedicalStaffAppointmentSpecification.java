package com.example.Hospital.Management.System.Repository.JPA;

import com.example.Hospital.Management.System.Model.DBModel.MedicalStaffAppointmentEntity;
import com.example.Hospital.Management.System.SearchCriteria.MedicalStaffAppointmentSearchCriteria;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MedicalStaffAppointmentSpecification {

    public static Specification<MedicalStaffAppointmentEntity> filterByCriteria(MedicalStaffAppointmentSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            // FIX: Forțăm încărcarea datelor relaționate (Appointment, Doctor, Nurse)
            // Asta previne obiectele goale în Mapper
            if (Long.class != query.getResultType()) {
                root.fetch("appointment", JoinType.LEFT);
                root.fetch("doctor", JoinType.LEFT);
                root.fetch("nurse", JoinType.LEFT);
            }

            List<Predicate> predicates = new ArrayList<>();

            if (criteria != null) {
                if (criteria.getAppointmentID() != null && !criteria.getAppointmentID().isEmpty()) {
                    try {
                        Long appId = Long.valueOf(criteria.getAppointmentID());
                        predicates.add(criteriaBuilder.equal(root.get("appointment").get("id"), appId));
                    } catch (NumberFormatException ignored) {}
                }

                if (criteria.getMedicalStaffID() != null && !criteria.getMedicalStaffID().isEmpty()) {
                    try {
                        Long staffId = Long.valueOf(criteria.getMedicalStaffID());
                        Predicate isDoctor = criteriaBuilder.equal(root.get("doctor").get("id"), staffId);
                        Predicate isNurse = criteriaBuilder.equal(root.get("nurse").get("id"), staffId);
                        predicates.add(criteriaBuilder.or(isDoctor, isNurse));
                    } catch (NumberFormatException ignored) {}
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}