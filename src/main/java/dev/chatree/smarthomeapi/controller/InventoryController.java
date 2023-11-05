package dev.chatree.smarthomeapi.controller;

import dev.chatree.smarthomeapi.entity.InventoryEntity;
import dev.chatree.smarthomeapi.model.ErrorResponse;
import dev.chatree.smarthomeapi.model.inventory.InventoryRequest;
import dev.chatree.smarthomeapi.model.inventory.InventoryResponse;
import dev.chatree.smarthomeapi.service.InventoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAllInventory(HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getInventoryById(@PathVariable Long id,
                                                   HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            InventoryResponse inventoryResponse = inventoryService.getInventoryById(id);
            return ResponseEntity.ok(inventoryResponse);
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getStatusText()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createInventory(@RequestBody InventoryRequest inventory,
                                                  HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        inventoryService.createInventory(inventory);
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateInventory(@PathVariable Long id,
                                                  @RequestBody InventoryRequest inventory,
                                                  HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            inventoryService.updateInventory(id, inventory);
            return ResponseEntity.ok().build();
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getStatusText()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInventory(@PathVariable Long id,
                                                  HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            inventoryService.deleteInventory(id);
            return ResponseEntity.noContent().build();
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getStatusText()));
        }
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> deleteInventory(@RequestParam String ids,
                                             HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        if (ids.isBlank()) {
            log.info("Error: ids must not be blank");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "ids must not be blank"));
        }

        List<String> idStringList = List.of(ids.split(","));
        try {
            List<Long> idList = idStringList.stream().map(Long::parseLong).toList();
            inventoryService.deleteMultipleInventory(idList);
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "ids must be a number"));
        }
    }
}
