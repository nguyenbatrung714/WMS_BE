package org.example.wms_be.mapper.report;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.data.report.ImportProductByMonth;

import java.util.List;

@Mapper
public interface ReportMapper {
    List<ImportProductByMonth> reportImportProductByMonth();

    List<ImportProductByMonth> reportExportProductByMonth();
}
