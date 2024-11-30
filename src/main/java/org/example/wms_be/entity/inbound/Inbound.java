package org.example.wms_be.entity.inbound;

import lombok.Getter;
import lombok.Setter;
import org.example.wms_be.enums.TrangThai;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class Inbound {
    private Integer sysIdInBound;
    private String maInBound;
    private String maKho;
    private String maPO;
    private Timestamp ngayNhap;
    private TrangThai trangThai;
    private Integer sysIdUser;
    private String fullName;
    private String tenKho;
    List<PurchaseDetailsIb> chiTietNhapHang;
}
