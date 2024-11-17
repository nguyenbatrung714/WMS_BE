package org.example.wms_be.mapper.purchase;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.inbound.Inbound;

import java.util.List;

@Mapper
public interface InboundMapper {
    int insertInbound(Inbound inbound);

    List<Inbound> getAllInbound();

    Inbound getInboundBySysId(Integer sysIdInbound);
}
