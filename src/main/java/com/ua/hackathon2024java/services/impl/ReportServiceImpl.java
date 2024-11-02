package com.ua.hackathon2024java.services.impl;

import com.ua.hackathon2024java.DTOs.report.ReportRequestDto;
import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import com.ua.hackathon2024java.entity.Report;
import com.ua.hackathon2024java.exceptions.BadRequestException;
import com.ua.hackathon2024java.factories.ReportDtoFactory;
import com.ua.hackathon2024java.repository.ReportRepository;
import com.ua.hackathon2024java.services.ReportService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ReportDtoFactory reportDtoFactory;

    @Override
    @Transactional
    public ReportResponseDto createReport(ReportRequestDto reportRequestDto) {
        Report report = Report.builder()
                .name(reportRequestDto.getName())
                .city(reportRequestDto.getCity())
                .number(reportRequestDto.getNumber())
                .text(reportRequestDto.getText())
                .build();

        return reportDtoFactory.makeReportDtoResponse(reportRepository.save(report));
    }

    @Override
    @Transactional(readOnly = true)
    public ReportResponseDto getReportResponseById(Long id) {
        return reportDtoFactory
                .makeReportDtoResponse(
                        reportRepository.findById(id)
                                .orElseThrow(() -> new BadRequestException("Report not found"))
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportResponseDto> findAll() {
        return reportRepository.findAll()
                .stream()
                .map(reportDtoFactory::makeReportDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportResponseDto> findAllSorted(String sortBy) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        return reportRepository.findAll(sort)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    private ReportResponseDto convertToResponseDto(Report report) {
        return ReportResponseDto.builder()
                .id(report.getId())
                .name(report.getName())
                .city(report.getCity())
                .number(report.getNumber())
                .text(report.getText())
//                .filesUrls(report.getFilesUrls())
                .createdAt(report.getCreatedAt())
//                .status(report.getStatus())
                .build();
    }
}
