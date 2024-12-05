package org.example.wms_be.mapper.inventory;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.data.response.InventoryResp;
import org.example.wms_be.entity.inventory.Inventory;

import java.util.List;

@Mapper
public interface InventoryMapper {
    List<InventoryResp> layDanhSachLoHangCanXuat(Integer sysIdSanPham);
    int updateInventory(Integer sysIdTonKho, Double soLuong);

    List<InventoryResp> getAllInventory();
    int updateSoLuongHienCo(Integer sysIdSanPham);
    Inventory getLohangById(Integer id);
    int deleteLohangById(Integer id);
}
