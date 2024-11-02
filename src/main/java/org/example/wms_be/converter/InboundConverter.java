package org.example.wms_be.converter;

import org.example.wms_be.data.request.InboundReq;
import org.example.wms_be.entity.inbound.Inbound;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InboundConverter {
    InboundReq toInboundReq(Inbound inbound);

    Inbound toInbound(InboundReq inboundReq);
}
