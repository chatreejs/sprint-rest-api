package dev.chatree.smarthomeapi.service;

import dev.chatree.smarthomeapi.entity.InventoryEntity;
import dev.chatree.smarthomeapi.model.InventoryDTO;
import dev.chatree.smarthomeapi.repository.InventoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Log4j2
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryEntity> getAllInventory() {
        List<InventoryEntity> inventoryEntityList = inventoryRepository.findAll();
        log.info("Found {} items", inventoryEntityList.size());
        log.info("Get all inventory done!");
        return inventoryEntityList;
    }

    public InventoryEntity getInventoryById(Long id) {
        log.info("id: {}", id);
        InventoryEntity inventoryEntity = inventoryRepository.findById(id).orElse(null);
        if (inventoryEntity == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Inventory not found");
        }
        log.info("getInventoryById done!");
        return inventoryEntity;
    }

    public void createInventory(InventoryDTO inventoryDTO) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setName(inventoryDTO.getName());
        inventoryEntity.setQuantity(inventoryDTO.getQuantity());
        inventoryEntity.setMaxQuantity(inventoryDTO.getMaxQuantity());
        inventoryEntity.setUnit(inventoryDTO.getUnit());

        inventoryRepository.save(inventoryEntity);
        log.info("createInventory done!");
    }

    public void updateInventory(Long id, InventoryDTO inventoryDTO) {
        log.info("id: {}", id);
        InventoryEntity inventoryEntity = inventoryRepository.findById(id).orElse(null);
        if (inventoryEntity == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Inventory not found");
        }
        inventoryEntity.setName(inventoryDTO.getName());
        inventoryEntity.setQuantity(inventoryDTO.getQuantity());
        inventoryEntity.setMaxQuantity(inventoryDTO.getMaxQuantity());
        inventoryEntity.setUnit(inventoryDTO.getUnit());

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
}
