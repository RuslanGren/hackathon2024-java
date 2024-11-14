package com.ua.hackathon2024java.services.impl;

import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import com.ua.hackathon2024java.services.PdfConverterService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

@Service
public class PdfConverterServiceImpl implements PdfConverterService {

    @Override
    public byte[] generatePdfForReports(List<ReportResponseDto> reports) throws IOException {
        try (PDDocument document = new PDDocument()) {
            for (ReportResponseDto report : reports) {
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
                    contentStream.showText("Date: " + report.getCreatedAt());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Status: " + report.getStatus());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("FirstName: " + report.getFirstName());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("LastName: " + report.getLastName());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("FatherName: " + report.getFatherName());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Number: " + report.getNumber());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Region: " + report.getRegion());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Address: " + report.getAddress());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Category: " + report.getCategory());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Text: " + report.getText());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("AdditionalInformation: " + report.getAdditionalInformation());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("url: " + report.getUrl());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.endText();
                }
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        }
    }

}
