package org.example.wms_be.service;

import org.example.wms_be.data.response.InventoryResp;

import java.util.List;

public interface InventoryService {
    List<InventoryResp> getInventoryList();

}
