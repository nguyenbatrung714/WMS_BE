package org.example.wms_be.service;

import org.example.wms_be.data.request.InboundReq;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InboundService {

    @Transactional
    InboundReq createInbound(InboundReq inboundReq);

    List<InboundReq> getAllInbound();
}
