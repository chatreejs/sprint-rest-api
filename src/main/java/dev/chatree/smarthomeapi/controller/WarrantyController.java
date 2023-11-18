package dev.chatree.smarthomeapi.controller;

import dev.chatree.smarthomeapi.model.ErrorResponse;
import dev.chatree.smarthomeapi.model.warranty.WarrantyRequest;
import dev.chatree.smarthomeapi.model.warranty.WarrantyResponse;
import dev.chatree.smarthomeapi.service.WarrantyService;
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
@RequestMapping("/warranties")
public class WarrantyController {

    private final WarrantyService warrantyService;

    public WarrantyController(WarrantyService warrantyService) {
        this.warrantyService = warrantyService;
    }

    @GetMapping
    public ResponseEntity<List<WarrantyResponse>> getAllWarranty() {
        log.info("API GET /warranties");
        return ResponseEntity.ok(warrantyService.getAllWarranty());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWarrantyById(@PathVariable Long id) {
        log.info("API GET /warranties/{}", id);
        try {
            WarrantyResponse warrantyResponse = warrantyService.getWarrantyById(id);
            return ResponseEntity.ok(warrantyResponse);
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getStatusText()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createWarranty(@RequestBody WarrantyRequest warranty) {
        log.info("API POST /warranties");
        warrantyService.createWarranty(warranty);
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWarranty(@PathVariable Long id,
                                            @RequestBody WarrantyRequest warranty) {
        log.info("API PUT /warranties/{}", id);
        try {
            warrantyService.updateWarranty(id, warranty);
            return ResponseEntity.ok().build();
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getStatusText()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWarranty(@PathVariable Long id) {
        log.info("API DELETE /warranties/{}", id);
        try {
            warrantyService.deleteWarranty(id);
            return ResponseEntity.ok().build();
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getStatusText()));
        }
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> deleteWarranty(@RequestParam String ids,
                                            HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        if (ids.isBlank()) {
            log.info("Error: ids must not be blank");
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "ids must not be blank"));
        }

        List<String> idStringList = List.of(ids.split(","));
        try {
            List<Long> idList = idStringList.stream().map(Long::parseLong).toList();
            warrantyService.deleteMultipleWarranty(idList);
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "ids must be a number"));
        }
    }
}
