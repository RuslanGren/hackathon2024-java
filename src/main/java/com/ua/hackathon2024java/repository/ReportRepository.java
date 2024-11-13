package com.ua.hackathon2024java.repository;

import com.ua.hackathon2024java.entity.Regions;
import com.ua.hackathon2024java.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByRegion(Regions region);
}
