package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.report.ImportProductByMonth;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.mapper.report.ReportMapper;
import org.example.wms_be.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportMapper reportMapper;

    @Override
    public List<ImportProductByMonth> reportImportProductByMonth() {
        try {
            return reportMapper.reportImportProductByMonth();
        } catch (Exception e) {
            throw new BadSqlGrammarException("Failed to get report import product by month" + e.getMessage());
        }
    }
}
