package org.example.wms_be.mapper.purchase;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.data.response.OutboundResp;
import org.example.wms_be.entity.outbound.OutBound;

import java.util.List;

@Mapper
public interface OutboundMapper {
    List<OutboundResp> getAllOutbound();
    int insertOutbound(OutBound outBound);
    OutBound getOutboundBySysId(Integer sysIdOutbound);
    boolean existById(Integer sysIdYeuCauXuatHang);
    String getMaOBById(Integer sysIdOutbound);
}
