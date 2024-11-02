package org.example.wms_be.mapper.purchase;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.inbound.Inbound;

@Mapper
public interface InboundMapper {
    int insertInbound(Inbound inbound);
}
