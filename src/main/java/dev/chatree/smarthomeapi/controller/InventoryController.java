package dev.chatree.smarthomeapi.controller;

import dev.chatree.smarthomeapi.model.ErrorResponse;
import dev.chatree.smarthomeapi.model.inventory.InventoryRequest;
import dev.chatree.smarthomeapi.service.InventoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<?> getAllInventory(
            @RequestParam("homeId") Long homeId,
            Authentication authentication,
            HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            var subject = authentication.getName();
            var inventoryResponseList = inventoryService.getAllInventory(homeId, subject);
            return ResponseEntity.ok(inventoryResponseList);
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode().value(), e.getStatusText()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInventoryById(@PathVariable Long id,
                                              @RequestParam("homeId") Long homeId,
                                              Authentication authentication,
                                              HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            var subject = authentication.getName();
            var inventoryResponse = inventoryService.getInventoryById(id, homeId, subject);
            return ResponseEntity.ok(inventoryResponse);
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getStatusText()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createInventory(@RequestParam("homeId") Long homeId,
                                             @RequestBody InventoryRequest inventory,
                                             Authentication authentication,
                                             HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            var subject = authentication.getName();
            inventoryService.createInventory(inventory, homeId, subject);
            return ResponseEntity.created(null).build();
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getStatusText()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInventory(@PathVariable Long id,
                                             @RequestParam("homeId") Long homeId,
                                             @RequestBody InventoryRequest inventory,
                                             Authentication authentication,
                                             HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            var subject = authentication.getName();
            inventoryService.updateInventory(id, inventory, homeId, subject);
            return ResponseEntity.ok().build();
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getStatusText()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable Long id,
                                             @RequestParam("homeId") Long homeId,
                                             Authentication authentication,
                                             HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            var subject = authentication.getName();
            inventoryService.deleteInventory(id, homeId, subject);
            return ResponseEntity.noContent().build();
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getStatusText()));
        }
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> deleteInventory(@RequestParam("homeId") Long homeId,
                                             Authentication authentication,
                                             String ids,
                                             HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        if (ids.isBlank()) {
            log.info("Error: ids must not be blank");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "ids must not be blank"));
        }

        List<String> idStringList = List.of(ids.split(","));
        try {
            var subject = authentication.getName();
            var idList = idStringList.stream().map(Long::parseLong).toList();
            inventoryService.deleteMultipleInventory(idList, homeId, subject);
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "ids must be a number"));
        }
    }
}
