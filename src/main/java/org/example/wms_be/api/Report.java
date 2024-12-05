package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.report.ImportProductByMonth;
import org.example.wms_be.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@AllArgsConstructor
@CrossOrigin
public class Report {
    private final ReportService reportService;

    @GetMapping("/import-product-by-months")
    public ResponseEntity<ApiResponse<List<ImportProductByMonth>>> reportImportProductByMonth(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "report import product by months",
                reportService.reportImportProductByMonth()
        ), HttpStatus.OK);

    }
}
