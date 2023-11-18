package dev.chatree.smarthomeapi.controller;

import dev.chatree.smarthomeapi.model.ErrorResponse;
import dev.chatree.smarthomeapi.model.food.FoodRequest;
import dev.chatree.smarthomeapi.model.food.FoodResponse;
import dev.chatree.smarthomeapi.service.FoodService;
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
@RequestMapping("/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public ResponseEntity<List<FoodResponse>> getAllFood(HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        return ResponseEntity.ok(foodService.getAllFood());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFoodById(@PathVariable Long id,
                                         HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            FoodResponse foodResponse = foodService.getFoodById(id);
            log.info("{}", foodResponse);
            return ResponseEntity.ok(foodResponse);
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getStatusText()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createFood(@RequestBody FoodRequest foodRequest,
                                        HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        foodService.createFood(foodRequest);
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFood(@PathVariable Long id,
                                        @RequestBody FoodRequest foodRequest,
                                        HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            foodService.updateFood(id, foodRequest);
            return ResponseEntity.ok().build();
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getStatusText()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id,
                                        HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            foodService.deleteFood(id);
            return ResponseEntity.noContent().build();
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getStatusText()));
        }
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> deleteMultipleFood(String ids,
                                                HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        if (ids.isBlank()) {
            log.info("Error: ids must not be blank");
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "ids must not be null"));
        }

        List<String> idStringList = List.of(ids.split(","));
        try {
            List<Long> idList = idStringList.stream().map(Long::parseLong).toList();
            foodService.deleteMultipleFood(idList);
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException e) {
            log.info("Error: {} {}", e.getMessage(), e.getClass().getSimpleName());
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "ids must be a number"));
        }
    }
}
