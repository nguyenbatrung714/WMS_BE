package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.constant.InventoryConst;
import org.example.wms_be.data.response.InventoryResp;
import org.example.wms_be.entity.inventory.KiemTraTonKho;
import org.example.wms_be.entity.inventory.LoSuDung;
import org.example.wms_be.mapper.inventory.InventoryMapper;
import org.example.wms_be.mapper.purchase.PurchaseDetailsObMapper;
import org.example.wms_be.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryMapper inventoryMapper;
    private final   PurchaseDetailsObMapper purchaseDetailsObMapper;
    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Override
    public List<InventoryResp> layDanhSachLoHangCanXuat(Integer sysIdSanPham) {
        try {
            return inventoryMapper.layDanhSachLoHangCanXuat(sysIdSanPham);
        } catch (Exception e) {
            logger.error(InventoryConst.GET_FIRSTOUT_FAILED);
            return Collections.emptyList();
        }
    }

    @Override
    public KiemTraTonKho kiemTraTonKho(Integer sysIdSanPham, Integer sysIdChiTietXuatHang) {

        double soLuongCanXuat = purchaseDetailsObMapper.getSoLuongCanXuat(sysIdChiTietXuatHang, sysIdSanPham);
        if ( soLuongCanXuat <= 0) {
            throw new IllegalArgumentException(InventoryConst.NOT_FOUND_INVENTORY +  sysIdChiTietXuatHang);
        }

        List<InventoryResp> inventoryResps = inventoryMapper.layDanhSachLoHangCanXuat(sysIdSanPham);


        double soLuongConThieu = soLuongCanXuat;
        List<LoSuDung> loSuDung = new ArrayList<>();


        for (InventoryResp inventoryResp : inventoryResps) {
            if (soLuongConThieu <= 0) break;

            double soLuongTru = Math.min(soLuongConThieu, inventoryResp.getSoLuong());
            soLuongConThieu -= soLuongTru;

            // Ghi lại các lô được sử dụng
            loSuDung.add(new LoSuDung(inventoryResp.getSysIdTonKho(), soLuongTru));
            inventoryMapper.updateInventory(inventoryResp.getSysIdTonKho(), inventoryResp.getSoLuong() - soLuongTru);

        }


        if (soLuongConThieu > 0) {
            throw new IllegalArgumentException(InventoryConst.NOT_ENOUGH_QUANTITY);
        }


        return new KiemTraTonKho(sysIdSanPham, loSuDung);
    }

}
