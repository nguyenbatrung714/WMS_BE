package org.example.wms_be.data.request;

import lombok.Getter;
import lombok.Setter;
import org.example.wms_be.enums.TypeOrder;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderIbReq {
    private Integer sysIdPO;
    private String maPO;
    private String maPR;
    private Timestamp ngayTao = new Timestamp(System.currentTimeMillis());
    private Integer sysIdUser;
    List<PurchaseDetailsIbReq> chiTietNhapHang;
}
