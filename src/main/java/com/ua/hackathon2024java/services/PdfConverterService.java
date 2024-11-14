package com.ua.hackathon2024java.services;

import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;

import java.io.IOException;
import java.util.List;

public interface PdfConverterService {
    byte[] generatePdfForReports(List<ReportResponseDto> reports) throws IOException;
}
