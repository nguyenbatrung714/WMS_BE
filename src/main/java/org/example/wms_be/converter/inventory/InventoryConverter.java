package org.example.wms_be.converter.inventory;

import org.example.wms_be.data.request.InventoryReq;
import org.example.wms_be.entity.inventory.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryConverter {
    Inventory toInventory(InventoryReq inventoryReq);

    InventoryReq toInventoryReq(Inventory inventory);
}
