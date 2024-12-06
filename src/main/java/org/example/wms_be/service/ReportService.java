package org.example.wms_be.service;

import org.example.wms_be.data.report.ImportProductByMonth;

import java.util.List;

public interface ReportService {
    List<ImportProductByMonth> reportImportProductByMonth();
}
