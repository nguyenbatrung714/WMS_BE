package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.response.thongke.ThongKeGiaoDichSanPhamResp;
import org.example.wms_be.data.response.thongke.ThongKeGiaoDichSanPhamGanDayResp;
import org.example.wms_be.mapper.inbound.PurchaseRequestIbMapper;
import org.example.wms_be.mapper.purchase.PurchaseRequestObMapper;
import org.example.wms_be.service.ThongKeGiaoDichSanPhamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThongKeGiaoDichSanPhamImpl implements ThongKeGiaoDichSanPhamService {
    private final PurchaseRequestIbMapper purchaseRequestIbMapper;
    private final PurchaseRequestObMapper purchaseRequestObMapper;
    @Override
    public ThongKeGiaoDichSanPhamGanDayResp getThongKeGiaoDichSanPham(boolean isNhap) {
        try {
            List<ThongKeGiaoDichSanPhamResp> giaoDichSanPhams;
            Integer giaoDichGanDay;

            if (isNhap){
                    giaoDichSanPhams = purchaseRequestIbMapper.getThongKeGiaoDichSanPham();
            }else{
                giaoDichSanPhams = purchaseRequestObMapper.getThongKeGiaoDichSanPhamXuat();
            }

            giaoDichGanDay   = purchaseRequestObMapper.giaoDichXuatGanDay();
            ThongKeGiaoDichSanPhamGanDayResp results = new ThongKeGiaoDichSanPhamGanDayResp();
            results.setThongKeGiaoDichSanPhamResp(giaoDichSanPhams);
            results.setGiaoDichGanDay(giaoDichGanDay);

            return results;

        }catch (Exception e){
            return new ThongKeGiaoDichSanPhamGanDayResp();
        }
    }
}
