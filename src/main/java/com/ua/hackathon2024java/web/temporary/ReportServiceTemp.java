package com.ua.hackathon2024java.web.temporary;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.poi.common.usermodel.fonts.FontGroup;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceTemp {

    public List<ReportResponseDtoTemp> findAllSortedAndFiltered(String sortBy, String filterCategory, String filterStatus, String filterRegion) {
        List<ReportResponseDtoTemp> reports = getDummyReports(); // Temporary list for testing

        if (filterCategory != null && !filterCategory.isEmpty()) {
            reports = reports.stream()
                    .filter(report -> filterCategory.equals(report.getCategory()))
                    .collect(Collectors.toList());
        }

        if (filterStatus != null && !filterStatus.isEmpty()) {
            reports = reports.stream()
                    .filter(report -> filterStatus.equals(report.getStatus()))
                    .collect(Collectors.toList());
        }
        if (filterRegion != null && !filterRegion.isEmpty()) {
            reports = reports.stream()
                    .filter(report -> filterRegion.equals(report.getRegion()))
                    .collect(Collectors.toList());
        }

        if (sortBy != null) {
            switch (sortBy) {
                case "date":
                    reports = reports.stream()
                            .sorted(Comparator.comparing(ReportResponseDtoTemp::getDate))
                            .collect(Collectors.toList());
                    break;
                case "status":
                    reports = reports.stream()
                            .sorted(Comparator.comparing(ReportResponseDtoTemp::getStatus))
                            .collect(Collectors.toList());
                    break;
                case "id":
                    reports = reports.stream()
                            .sorted(Comparator.comparing(ReportResponseDtoTemp::getId))
                            .collect(Collectors.toList());
                    break;
                case "name":
                    reports = reports.stream()
                            .sorted(Comparator.comparing(ReportResponseDtoTemp::getName))
                            .collect(Collectors.toList());
                    break;
                case "region":
                    reports = reports.stream()
                            .sorted(Comparator.comparing(ReportResponseDtoTemp::getRegion))
                            .collect(Collectors.toList());
                    break;
                case "city":
                    reports = reports.stream()
                            .sorted(Comparator.comparing(ReportResponseDtoTemp::getCity))
                            .collect(Collectors.toList());
                    break;
                case "category":
                    reports = reports.stream()
                            .sorted(Comparator.comparing(ReportResponseDtoTemp::getCategory))
                            .collect(Collectors.toList());
                    break;
            }
        }

        return reports;
    }
    public byte[] generatePdfForReports(List<ReportResponseDtoTemp> reports) throws IOException {
        try (PDDocument document = new PDDocument()) {
            for (ReportResponseDtoTemp report : reports) {
                PDPage page = new PDPage();
                document.addPage(page);
                InputStream fontStream = getClass().getResourceAsStream("/arial.ttf");
                PDType0Font font = PDType0Font.load(document, fontStream);
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.beginText();
                    contentStream.setFont(font, 12);
                    contentStream.newLineAtOffset(50, 700);
                    contentStream.showText("ID: " + report.getId());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Date: " + report.getDate());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Status: " + report.getStatus());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Name: " + report.getName());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Region: " + report.getRegion());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("City: " + report.getCity());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Category: " + report.getCategory());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Text: " + report.getText());
                    contentStream.endText();
                }
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        }
    }


    public List<ReportResponseDtoTemp> findReportsByIds(List<Long> reportIds) {
        List<ReportResponseDtoTemp> reports = getDummyReports(); // Отримуємо всі скарги

        // Фільтруємо скарги за ID, що містяться в reportIds
        return reports.stream()
                .filter(report -> reportIds.contains(report.getId()))
                .collect(Collectors.toList());
    }

    private List<ReportResponseDtoTemp> getDummyReports() {
        // Creating dummy data for testing
        List<ReportResponseDtoTemp> reports = new ArrayList<>();
        reports.add(new ReportResponseDtoTemp(1L, "2024-01-01", "Open", "Complaint text 1", "John Doe", "Kyiv", "Kyiv", "Корупція"));
        reports.add(new ReportResponseDtoTemp(2L, "2024-01-03", "Closed", "Complaint text 2", "Jane Doe", "Lviv", "Lviv", "Зловживання владою"));
        reports.add(new ReportResponseDtoTemp(3L, "2024-01-02", "In Progress", "Complaint text 3", "Jim Beam", "Odessa", "Odessa", "Шахрайство"));
        return reports;
    }

}
