package org.example.wms_be.data.request;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseDetailsIbReq {
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
    private String  tenSanPham;
    private String  tenKhachHang;
    private Integer sysIdNhaCungCap;
    private String tenNhaCungCap;
}
