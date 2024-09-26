package com._x.service;

import com._x.model.Candidate;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;


public class CandidateSpecification {

    private CandidateSpecification(){

    }

    public static Specification<Candidate> withFilters(
            String candidateName,
            String experience,
            String noticePeriod,
            String education,
            String skills,
            String phone,
            String email,
            String source,
            String history,
            Boolean isOpenToWork,
            String contactStatus
    ) {

        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            var predicates = new ArrayList<Predicate>();

            if (candidateName != null && !candidateName.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("candidateName")), "%" + candidateName.toLowerCase() + "%"));
            }
            if (experience != null && !experience.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("experience")), "%" + experience.toLowerCase() + "%"));
            }
            if (noticePeriod != null && !noticePeriod.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("noticePeriod")), "%" + noticePeriod.toLowerCase() + "%"));
            }
            if (education != null && !education.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("education")), "%" + education.toLowerCase() + "%"));
            }
            if (skills != null && !skills.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("skills")), "%" + skills.toLowerCase() + "%"));
            }
            if (phone != null && !phone.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + phone.toLowerCase() + "%"));
            }
            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
            }
            if (source != null && !source.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("source")), "%" + source.toLowerCase() + "%"));
            }
            if (history != null && !history.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("history")), "%" + history.toLowerCase() + "%"));
            }
            if (isOpenToWork != null) {
                predicates.add(criteriaBuilder.equal(root.get("isOpenToWork"), isOpenToWork));
            }
            if (contactStatus != null && !contactStatus.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("contactStatus")), "%" + contactStatus.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}