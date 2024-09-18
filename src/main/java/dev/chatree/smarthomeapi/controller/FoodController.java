package dev.chatree.smarthomeapi.controller;

import dev.chatree.smarthomeapi.model.ErrorResponse;
import dev.chatree.smarthomeapi.model.food.FoodRequest;
import dev.chatree.smarthomeapi.model.food.FoodResponse;
import dev.chatree.smarthomeapi.service.AccountService;
import dev.chatree.smarthomeapi.service.FoodService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.management.ConstructorParameters;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<?> getAllFood(@RequestParam("homeId") Long homeId,
                                        Authentication authentication,
                                        HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            var subject = authentication.getName();
            var foodResponseList = foodService.getAllFood(homeId, subject);
            return ResponseEntity.ok(foodResponseList);
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode().value(), e.getStatusText()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFoodById(@RequestParam("homeId") Long homeId,
                                         @PathVariable Long id,
                                         Authentication authentication,
                                         HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            var subject = authentication.getName();
            var foodResponse = foodService.getFoodById(id, homeId, subject);
            return ResponseEntity.ok(foodResponse);
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode().value(), e.getStatusText()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createFood(@RequestParam("homeId") Long homeId,
                                        @RequestBody FoodRequest foodRequest,
                                        Authentication authentication,
                                        HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            var subject = authentication.getName();
            foodService.createFood(foodRequest, homeId, subject);
            return ResponseEntity.created(null).build();
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode().value(), e.getStatusText()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFood(@PathVariable Long id,
                                        @RequestParam("homeId") Long homeId,
                                        @RequestBody FoodRequest foodRequest,
                                        Authentication authentication,
                                        HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            var subject = authentication.getName();
            foodService.updateFood(id, foodRequest, homeId, subject);
            return ResponseEntity.ok().build();
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode().value(), e.getStatusText()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id,
                                        @RequestParam("homeId") Long homeId,
                                        Authentication authentication,
                                        HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            var subject = authentication.getName();
            foodService.deleteFood(id, homeId, subject);
            return ResponseEntity.noContent().build();
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode().value(), e.getStatusText()));
        }
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> deleteMultipleFood(@RequestParam("homeId") Long homeId,
                                                Authentication authentication,
                                                String ids,
                                                HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        if (ids.isBlank()) {
            log.info("Error: ids must not be blank");
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "ids must not be null"));
        }

        var idStringList = List.of(ids.split(","));
        try {
            var subject = authentication.getName();
            var idList = idStringList.stream().map(Long::parseLong).toList();
            foodService.deleteMultipleFood(idList, homeId, subject);
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException e) {
            log.info("Error: {} {}", e.getMessage(), e.getClass().getSimpleName());
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "ids must be a number"));
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode().value(), e.getStatusText()));
        }
    }
}
