package com.ua.hackathon2024java.services.impl;

import com.ua.hackathon2024java.DTOs.report.ReportRequestDto;
import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import com.ua.hackathon2024java.entity.*;
import com.ua.hackathon2024java.exceptions.BadRequestException;
import com.ua.hackathon2024java.factories.ReportDtoFactory;
import com.ua.hackathon2024java.repository.ReportRepository;
import com.ua.hackathon2024java.services.MailService;
import com.ua.hackathon2024java.services.PdfConverterService;
import com.ua.hackathon2024java.services.ReportService;

import com.ua.hackathon2024java.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ReportDtoFactory reportDtoFactory;
    private final UserService userService;
    private final PdfConverterService pdfConverterService;
    private final MailService mailService;

    @Override
    @Transactional
    public String changeStatus(Long id, String status) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new BadRequestException("Report not found"));


        Sort.Direction direction = Sort.Direction.fromString("asc");
        List<Report> userReports = filterAndSortReports(null, null, null, null, "id", direction);

        if (userReports.contains(report)) {
            report.setStatus(ReportStatus.fromLabel(status));
            return "success";
        } else {
            throw new BadRequestException("Report does not meet the filter criteria");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportResponseDto> filterAndSortReportsResponseDto(
            ReportStatus status,
            ReportCategory category,
            Instant createdAfter,
            Instant createdBefore,
            String sortBy,
            Sort.Direction direction)
    {
        return filterAndSortReports(status, category, createdAfter, createdBefore, sortBy, direction).stream()
                .map(reportDtoFactory::makeReportDtoResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> filterAndSortReports(
            ReportStatus status, ReportCategory category,
            Instant createdAfter, Instant createdBefore,
            String sortBy, Sort.Direction direction
    ) {

        User user = userService.getUser();

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ADMIN"));

        Specification<Report> spec = Specification.where(ReportSpecifications.hasStatus(status))
                .and(ReportSpecifications.hasCategory(category))
                .and(ReportSpecifications.createdAtAfter(createdAfter))
                .and(ReportSpecifications.createdAtBefore(createdBefore));

        if (!isAdmin) {
            Regions userRegion = user.getRoles().stream()
                    .filter(role -> role.getName().startsWith("REGION_"))
                    .findFirst()
                    .map(role -> Regions.getRegion(role.getName()))
                    .orElseThrow(() -> new RuntimeException("User does not have a region role"));

            spec = spec.and(ReportSpecifications.hasRegion(userRegion));
        }

        Sort sort = Sort.by(direction, sortBy);

        return reportRepository.findAll(spec, sort);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] downloadPdf(Iterable<Long> reportIds) {

        List<ReportResponseDto> reports = reportRepository.findAllById(reportIds)
                .stream()
                .map(reportDtoFactory::makeReportDtoResponse)
                .collect(Collectors.toList());

        if (reports.isEmpty()) {
            throw new BadRequestException("Reports not found");
        }

        byte[] result;
        try {
            result = pdfConverterService.generatePdfForReports(reports);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    @Transactional
    public ReportResponseDto createReport(ReportRequestDto reportRequestDto) {
        Regions region = Regions.getRegion(reportRequestDto.getRegion());

        mailService.sendMailsByRegion(region);

        Report report = Report.builder()
                .firstName(reportRequestDto.getFirstName())
                .lastName(reportRequestDto.getLastName())
                .fatherName(reportRequestDto.getFatherName())
                .number(reportRequestDto.getNumber())
                .region(region)
                .address(reportRequestDto.getAddress())
                .text(reportRequestDto.getText())
                .status(ReportStatus.PENDING)
                .category(ReportCategory.fromLabel(reportRequestDto.getCategory()))
                .additionalInformation(reportRequestDto.getAdditionalInformation())
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
}
