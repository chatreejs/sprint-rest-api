package com.chatreejs.smarthomeapi.controller;

import com.chatreejs.smarthomeapi.bean.request.FoodRequest;
import com.chatreejs.smarthomeapi.domain.Food;
import com.chatreejs.smarthomeapi.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping
    public ResponseEntity<List<Food>> getAllFood() {
        return ResponseEntity.ok(foodService.getAllFood());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) {
        Food food = foodService.getFoodById(id);
        if (food == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(food);
    }

    @PostMapping
    public ResponseEntity<Food> createFood(@Valid @RequestBody FoodRequest request) {
        foodService.createFood(request);
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @Valid @RequestBody FoodRequest request) {
        Food food = foodService.getFoodById(id);
        if (food == null) {
            return ResponseEntity.notFound().build();
        }
        foodService.updateFood(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Food> deleteFood(@PathVariable Long id) {
        Food food = foodService.getFoodById(id);
        if (food == null) {
            return ResponseEntity.notFound().build();
        }
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<Food> deleteMultipleFood(String ids) {
        String[] idList = ids.split(",");
        for (String id : idList) {
            Food food = foodService.getFoodById(Long.parseLong(id));
            if (food == null) {
                return ResponseEntity.notFound().build();
            }
            foodService.deleteFood(Long.parseLong(id));
        }
        return ResponseEntity.noContent().build();
    }
}
