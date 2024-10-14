package org.example.wms_be.data.request;

import lombok.Getter;
import lombok.Setter;
import org.example.wms_be.data.dto.PurchaseRequestDetailsDto;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderReq {
    private Integer sysIdPO;
    private String maPO;
    private String maPR;
    private Timestamp ngayTao = new Timestamp(System.currentTimeMillis());
    private Boolean trangThai = true;
    private Integer sysIdUser;
    List<PurchaseRequestDetailsDto> chiTietDonHang;
}
