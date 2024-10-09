package com.ua.hackathon2024java.repository;

import com.ua.hackathon2024java.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
