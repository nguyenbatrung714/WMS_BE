package org.example.wms_be.service;

import org.example.wms_be.data.request.ConsignmentReq;

import java.util.List;

public interface ConsignmentService {
    List<ConsignmentReq> getAllConsignment();

    ConsignmentReq saveConsignment(ConsignmentReq consignmentReq);

    Void deleteConsignment(String maLo);

    ConsignmentReq getAllConsignmentById(String maLo);
}
