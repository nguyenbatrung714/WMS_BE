package org.example.wms_be.data.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OutboundReq {
    private Integer sysIdOutbound;
    private String maOB;
    private String ngayXuat;
    private String trangThai;
    private Integer nguoiPhuTrach;
    List<PurchaseRequestDetailsObReq> chiTietXuatHang;
}
