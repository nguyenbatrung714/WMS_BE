package org.example.wms_be.data.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseRequestDetailsDto {
    private Integer sysIdChiTietDonHang;
    private String  maInBound;
    private String  maPR;
    private String  maPO;
    private Integer  maKhachHang;
    private Integer  maSanPham;
    private Double  soLuong;
    private Double  gia;
    private Double  tongChiPhi;
    private String  ngayNhap;
}
