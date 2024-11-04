package com.ua.hackathon2024java.web.controllers;

import com.ua.hackathon2024java.web.temporary.Inspector;
import com.ua.hackathon2024java.web.temporary.InspectorService;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final InspectorService inspectorService;

    public AdminController(InspectorService inspectorService) {
        this.inspectorService = inspectorService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<String> regions = List.of("Kyiv", "Lviv", "Odesa"); // Available regions
        List<Inspector> inspectors = inspectorService.getInspectors();

        model.addAttribute("regions", regions);
        model.addAttribute("inspectors", inspectors);
        return "admin";  // Refers to admin.html
    }

    @PostMapping("/inspectors")
    public String createInspector(@RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam List<String> regions) {
        inspectorService.createInspector(email, password, regions);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/inspectors/{id}")
    public String editInspectorRegions(@PathVariable Long id, @RequestParam List<String> regions) {
        inspectorService.updateInspectorRegions(id, regions);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/inspectors/delete/{id}")
    public String deleteInspector(@PathVariable Long id) {
        inspectorService.deleteInspector(id);
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/inspectors/export")
    public ResponseEntity<byte[]> exportInspectorsToExcel() {
        List<Inspector> inspectors = inspectorService.getInspectors();

        try (Workbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = (XSSFSheet) workbook.createSheet("Inspectors");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Email");
            headerRow.createCell(1).setCellValue("Password");
            headerRow.createCell(2).setCellValue("Regions");

            // Fill data rows
            int rowNum = 1;
            for (Inspector inspector : inspectors) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(inspector.getEmail());
                row.createCell(1).setCellValue(inspector.getPassword());
                row.createCell(2).setCellValue(String.join(", ", inspector.getRegions()));
            }

            // Statistics
            Map<String, Integer> regionCounts = new HashMap<>();
            for (Inspector inspector : inspectors) {
                for (String region : inspector.getRegions()) {
                    regionCounts.put(region, regionCounts.getOrDefault(region, 0) + 1);
                }
            }

            // Create statistics sheet
            XSSFSheet statsSheet = (XSSFSheet) workbook.createSheet("Statistics");
            Row statsHeaderRow = statsSheet.createRow(0);
            statsHeaderRow.createCell(0).setCellValue("Region");
            statsHeaderRow.createCell(1).setCellValue("Count");

            int statsRowNum = 1;
            for (Map.Entry<String, Integer> entry : regionCounts.entrySet()) {
                Row row = statsSheet.createRow(statsRowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            // Create the drawing patriarch for the statistics sheet
            XSSFDrawing drawing = statsSheet.createDrawingPatriarch();

            // Define anchor points for the chart
            XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, statsRowNum + 1, 10, 25);

            // Create the chart
            XSSFChart chart = drawing.createChart(anchor);
            chart.setTitleText("Inspectors per Region");
            chart.setTitleOverlay(false);

            // Create the chart data
            XDDFDataSource<String> regionsData = XDDFDataSourcesFactory.fromStringCellRange(statsSheet, new CellRangeAddress(1, statsRowNum - 1, 0, 0));
            XDDFNumericalDataSource<Double> countsData = XDDFDataSourcesFactory.fromNumericCellRange(statsSheet, new CellRangeAddress(1, statsRowNum - 1, 1, 1));

            // Create the chart plot
            XDDFChartAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
            bottomAxis.setTitle("Regions");
            XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
            leftAxis.setTitle("Count");

            XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
            XDDFChartData.Series series = data.addSeries(regionsData, countsData);
            series.setTitle("Inspectors Count", null);
            chart.plot(data);

            // Write the output to a byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] excelData = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "inspectors.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(excelData);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
