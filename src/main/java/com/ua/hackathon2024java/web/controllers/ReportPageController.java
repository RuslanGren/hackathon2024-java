package com.ua.hackathon2024java.web.controllers;

import com.ua.hackathon2024java.googleservices.GoogleDriveService;
import com.ua.hackathon2024java.web.temporary.ReportResponseDtoTemp;
import com.ua.hackathon2024java.web.temporary.ReportServiceTemp;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ReportPageController {

    private final ReportServiceTemp reportServiceTemp;
    private final GoogleDriveService googleDriveService;

    public ReportPageController(ReportServiceTemp reportServiceTemp, GoogleDriveService googleDriveService) {
        this.reportServiceTemp = reportServiceTemp;
        this.googleDriveService = googleDriveService;
    }

    @GetMapping("/report")
    public String homePage(@RequestParam(required = false) String sortBy,
                           @RequestParam(required = false) String filterCategory,
                           @RequestParam(required = false) String filterStatus,
                           @RequestParam(required = false) String filterRegion,
                           Model model) {
        List<ReportResponseDtoTemp> reports = reportServiceTemp.findAllSortedAndFiltered(sortBy, filterCategory, filterStatus, filterRegion);

        model.addAttribute("reports", reports);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("filterCategory", filterCategory);
        model.addAttribute("filterStatus", filterStatus);
        model.addAttribute("filterRegion", filterRegion);

        if (reports.isEmpty()) {
            model.addAttribute("message", "Немає доступних заявок.");
        }

        return "report"; // Назва шаблону для домашньої сторінки
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam List<Long> reportIds,
                                              @RequestParam String folderName) throws IOException {
        List<ReportResponseDtoTemp> reports = reportServiceTemp.findReportsByIds(reportIds);
        byte[] pdfData = reportServiceTemp.generatePdfForReports(reports);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reports.pdf");

        MultipartFile multipartFile = new MockMultipartFile("file", "reports.pdf", "application/pdf", pdfData);

        // Створіть папку з вказаною назвою
        String folderId = googleDriveService.createFolder(folderName, null);

        // Завантажте файл у створену папку
        String fileId = googleDriveService.uploadFile(multipartFile, folderId);

        return ResponseEntity.ok().headers(headers).body(pdfData);
    }
}
