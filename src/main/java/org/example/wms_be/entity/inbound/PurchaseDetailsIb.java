package org.example.wms_be.entity.inbound;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseDetailsIb {
    private Integer sysIdChiTietNhapHang;
    private String  maInBound;
    private String  maPR;
    private String  maPO;
    private Integer  sysIdKhachHang;
    private Integer  sysIdSanPham;
    private Double  soLuong;
    private Double  gia;
    private Double  tongChiPhi;
    private String  ngayNhapDuKien;
    private String maLoHang;
}
