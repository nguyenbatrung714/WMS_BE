package org.example.wms_be.converter;

import org.example.wms_be.data.request.OutboundReq;
import org.example.wms_be.entity.outbound.OutBound;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OutboundConverter {
    OutboundReq toOutboundReq(OutBound outbound);
    OutBound toOutbound(OutboundReq outboundReq);
}
