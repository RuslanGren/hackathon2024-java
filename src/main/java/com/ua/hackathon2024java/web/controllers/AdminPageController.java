package com.ua.hackathon2024java.web.controllers;

import com.ua.hackathon2024java.DTOs.user.UserRequestDto;
import com.ua.hackathon2024java.DTOs.user.UserResponseDto;
import com.ua.hackathon2024java.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminPageController {
    private final UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<UserResponseDto> users = userService.findAllUserResponseDto();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute("userRequestDto") UserRequestDto userRequestDto) {
        userService.createNewUser(userRequestDto);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/users/{id}/roles")
    public String updateUserRole(@PathVariable Long id, @RequestParam Set<String> roles) {
        userService.updateUserRoles(id, roles); // оновлення ролей для користувача
        return "redirect:/admin/dashboard"; // перенаправлення на список користувачів
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/users/export")
    public ResponseEntity<byte[]> exportUsersToExcel() {
        List<UserResponseDto> users = userService.findAllUserResponseDto();

        try (Workbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = (XSSFSheet) workbook.createSheet("Users");

            // Створення заголовку таблиці
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Email");
            headerRow.createCell(1).setCellValue("Password");
            headerRow.createCell(2).setCellValue("Roles");

            // Заповнення даних таблиці
            int rowNum = 1;
            for (UserResponseDto user : users) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getEmail());
                row.createCell(2).setCellValue(String.join(", ", user.getRoles()));
            }

            // Статистика по ролях
            Map<String, Integer> roleCounts = new HashMap<>();
            for (UserResponseDto user : users) {
                for (String role : user.getRoles()) {
                    roleCounts.put(role, roleCounts.getOrDefault(role, 0) + 1);
                }
            }

            // Створення окремого листа для статистики
            XSSFSheet statsSheet = (XSSFSheet) workbook.createSheet("Role Statistics");
            Row statsHeaderRow = statsSheet.createRow(0);
            statsHeaderRow.createCell(0).setCellValue("Role");
            statsHeaderRow.createCell(1).setCellValue("Count");

            int statsRowNum = 1;
            for (Map.Entry<String, Integer> entry : roleCounts.entrySet()) {
                Row row = statsSheet.createRow(statsRowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            // Створення графіку
            XSSFDrawing drawing = statsSheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, statsRowNum + 1, 10, 25);
            XSSFChart chart = drawing.createChart(anchor);
            chart.setTitleText("Users per Role");
            chart.setTitleOverlay(false);

            XDDFDataSource<String> rolesData = XDDFDataSourcesFactory.fromStringCellRange(statsSheet, new CellRangeAddress(1, statsRowNum - 1, 0, 0));
            XDDFNumericalDataSource<Double> countsData = XDDFDataSourcesFactory.fromNumericCellRange(statsSheet, new CellRangeAddress(1, statsRowNum - 1, 1, 1));

            XDDFChartAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
            bottomAxis.setTitle("Roles");
            XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
            leftAxis.setTitle("Count");

            XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
            XDDFChartData.Series series = data.addSeries(rolesData, countsData);
            series.setTitle("Users Count", null);
            chart.plot(data);

            // Записати результат в байтовий масив
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] excelData = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "users.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(excelData);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
