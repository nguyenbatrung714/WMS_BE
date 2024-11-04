package org.example.wms_be.mapper.inventory;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.inventory.Consignment;

import java.util.List;
import org.example.wms_be.entity.inventory.Consignment;

@Mapper
public interface ConsignmentMapper {
    List<Consignment> getAllConsignment();

    int insertConsignment(Consignment consignment);

    int updateConsignment(Consignment consignment);

    int deleteConsignment(String maLo);

    boolean checkConsignmentExists(String maLo);

    Consignment getConsignmentByMaLo(String maLo);
}
