package org.example.wms_be.service;

import org.example.wms_be.data.request.ConsignmentInbound;
import org.example.wms_be.data.request.ConsignmentReq;
import org.example.wms_be.data.response.ConsignmentResp;

import java.util.List;

public interface ConsignmentService {
    List<ConsignmentReq> getAllConsignment();

    ConsignmentReq saveConsignment(ConsignmentReq consignmentReq);

    Void deleteConsignment(String maLo);

    ConsignmentReq getAllConsignmentById(String maLo);

    ConsignmentResp getInfoLoHang(String maLo);

    List<ConsignmentReq> saveConsignmentFromInbound(ConsignmentInbound consignmentInbound);
    Double thongKeTongGiaTriKhoHang();
}
