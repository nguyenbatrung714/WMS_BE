package org.example.wms_be.converter;

import org.example.wms_be.data.request.InboundReq;
import org.example.wms_be.entity.purchase.Inbound;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InboundConverter {
    InboundReq toInboundReq(Inbound inbound);

    Inbound toInbound(InboundReq inboundReq);
}
