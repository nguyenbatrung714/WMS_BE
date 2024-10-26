package org.example.wms_be.entity.purchase;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PurchaseOrder {
    private Integer sysIdPO;
    private String maPO;
    private String maPR;
    private Timestamp ngayTao;
    private Boolean trangThai;
    private Integer sysIdUser;
    List<PurchaseRequestDetails> chiTietDonHang;
}
