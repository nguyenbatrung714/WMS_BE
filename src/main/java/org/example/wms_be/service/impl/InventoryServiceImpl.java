package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.response.InventoryResp;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.mapper.inventory.InventoryMapper;
import org.example.wms_be.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryMapper inventoryMapper;

    @Override
    public List<InventoryResp> getInventoryList() {
        try {
            return inventoryMapper.getAllInventory();
        } catch (Exception e) {
            throw new BadSqlGrammarException("Failed to get inventory list" + e.getMessage());
        }
    }
}
