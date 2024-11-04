package org.example.wms_be.mapper.inventory;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.inventory.Consignment;

@Mapper
public interface ConsignmentMapper {
    int insertConsignment(Consignment consignment);
}
