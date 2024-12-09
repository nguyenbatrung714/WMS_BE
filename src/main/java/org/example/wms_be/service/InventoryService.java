package org.example.wms_be.service;

import org.example.wms_be.data.response.InventoryResp;
import org.example.wms_be.entity.inventory.KiemTraTonKho;

import java.util.List;

public interface InventoryService {
    List<InventoryResp> layDanhSachLoHangCanXuat(Integer sysIdSanPham);
    KiemTraTonKho kiemTraTonKho(Integer sysIdSanPham, Integer sysIdChiTietXuatHang);
    List<InventoryResp> getInventoryList();
}
