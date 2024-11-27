package org.example.wms_be.service;

import org.example.wms_be.data.request.OutboundReq;
import org.example.wms_be.data.response.OutboundResp;
import org.example.wms_be.entity.outbound.OutBound;

import java.util.List;

public interface OutboundService {
    List<OutboundResp> getAllOutbound();
    void saveOutbound(OutboundReq outboundReq);
}
