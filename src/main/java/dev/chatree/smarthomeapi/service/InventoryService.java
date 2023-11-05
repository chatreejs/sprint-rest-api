package dev.chatree.smarthomeapi.service;

import dev.chatree.smarthomeapi.entity.InventoryEntity;
import dev.chatree.smarthomeapi.model.inventory.InventoryRequest;
import dev.chatree.smarthomeapi.model.inventory.InventoryResponse;
import dev.chatree.smarthomeapi.model.inventory.InventoryStatus;
import dev.chatree.smarthomeapi.repository.InventoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryResponse> getAllInventory() {
        List<InventoryEntity> inventoryEntityList = inventoryRepository.findAll();
        log.info("Found {} items", inventoryEntityList.size());
        List<InventoryResponse> inventoryResponseList = new ArrayList<>();

        for (InventoryEntity inventoryEntity : inventoryEntityList) {
            InventoryResponse inventoryResponse = generateInventoryResponse(inventoryEntity);
            inventoryResponseList.add(inventoryResponse);
        }
        log.info("Get all inventory done!");
        return inventoryResponseList;
    }

    public InventoryResponse getInventoryById(Long id) {
        log.info("id: {}", id);
        InventoryEntity inventoryEntity = inventoryRepository.findById(id).orElse(null);
        if (inventoryEntity == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Inventory not found");
        }

        InventoryResponse inventoryResponse = generateInventoryResponse(inventoryEntity);

        log.info("getInventoryById done!");
        return inventoryResponse;
    }

    public void createInventory(InventoryRequest inventoryRequest) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setName(inventoryRequest.getName());
        inventoryEntity.setQuantity(inventoryRequest.getQuantity());
        inventoryEntity.setMaxQuantity(inventoryRequest.getMaxQuantity());
        inventoryEntity.setUnit(inventoryRequest.getUnit());

        inventoryRepository.save(inventoryEntity);
        log.info("createInventory done!");
    }

    public void updateInventory(Long id, InventoryRequest inventoryRequest) {
        log.info("id: {}", id);
        InventoryEntity inventoryEntity = inventoryRepository.findById(id).orElse(null);
        if (inventoryEntity == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Inventory not found");
        }
        inventoryEntity.setName(inventoryRequest.getName());
        inventoryEntity.setQuantity(inventoryRequest.getQuantity());
        inventoryEntity.setMaxQuantity(inventoryRequest.getMaxQuantity());
        inventoryEntity.setUnit(inventoryRequest.getUnit());

        inventoryRepository.save(inventoryEntity);
        log.info("updateInventory done!");
    }

    public void deleteInventory(Long id) {
        log.info("id: {}", id);
        InventoryEntity inventoryEntity = inventoryRepository.findById(id).orElse(null);
        if (inventoryEntity == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Inventory not found");
        }
        inventoryRepository.deleteById(id);
        log.info("deleteInventory done!");
    }

    public void deleteMultipleInventory(List<Long> ids) {
        log.info("ids: {}", ids);
        inventoryRepository.deleteAllById(ids);
        log.info("deleteMultipleInventory done!");
    }

    private InventoryResponse generateInventoryResponse(InventoryEntity inventoryEntity) {
        InventoryResponse inventoryResponse = new InventoryResponse();
        inventoryResponse.setId(inventoryEntity.getId());
        inventoryResponse.setName(inventoryEntity.getName());
        inventoryResponse.setQuantity(inventoryEntity.getQuantity());
        inventoryResponse.setMaxQuantity(inventoryEntity.getMaxQuantity());
        inventoryResponse.setUnit(inventoryEntity.getUnit());
        inventoryResponse.setStatus(checkInventoryStatus(inventoryEntity.getQuantity(), inventoryEntity.getMaxQuantity()));

        return inventoryResponse;
    }

    private InventoryStatus checkInventoryStatus(Double quantity, Double maxQuantity) {
        if (quantity == 0) {
            return InventoryStatus.OUT_OF_STOCK;
        } else if (quantity <= (maxQuantity * 0.3)) {
            return InventoryStatus.LOW_STOCK;
        } else {
            return InventoryStatus.IN_STOCK;
        }
    }
}
