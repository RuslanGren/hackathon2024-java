package com.ua.hackathon2024java.entity;

import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class ReportSpecifications {

    // Фільтрація за статусом
    public static Specification<Report> hasStatus(ReportStatus status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    // Фільтрація за категорією
    public static Specification<Report> hasCategory(ReportCategory category) {
        return (root, query, criteriaBuilder) ->
                category == null ? null : criteriaBuilder.equal(root.get("category"), category);
    }

    // Фільтрація за датою створення (після)
    public static Specification<Report> createdAtAfter(Instant date) {
        return (root, query, criteriaBuilder) ->
                date == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), date);
    }

    // Фільтрація за датою створення (до)
    public static Specification<Report> createdAtBefore(Instant date) {
        return (root, query, criteriaBuilder) ->
                date == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), date);
    }

    public static Specification<Report> hasRegion(Regions region) {
        return (root, query, criteriaBuilder) ->
                region == null ? null : criteriaBuilder.equal(root.get("region"), region);
    }
}
