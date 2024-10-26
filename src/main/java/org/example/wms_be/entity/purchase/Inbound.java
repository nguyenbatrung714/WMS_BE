package org.example.wms_be.entity.purchase;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class Inbound {
    private String maInBound;
    private String maPO;
    private Timestamp ngayNhap;
    private TrangThai trangThai;
    private Integer sysIdUser;
    List<PurchaseRequestDetails> chiTietDonHang;
}
