package dev.chatree.smarthomeapi.controller;

import dev.chatree.smarthomeapi.dto.FoodDTO;
import dev.chatree.smarthomeapi.entity.FoodEntity;
import dev.chatree.smarthomeapi.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping
    public ResponseEntity<List<FoodEntity>> getAllFood() {
        return ResponseEntity.ok(foodService.getAllFood());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFoodById(@PathVariable Long id) {
        FoodEntity food = foodService.getFoodById(id);
        if (food == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(food);
    }

    @PostMapping
    public ResponseEntity<Object> createFood(@RequestBody FoodDTO request) {
        foodService.createFood(request);
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFood(@PathVariable Long id,@RequestBody FoodDTO request) {
        FoodEntity food = foodService.getFoodById(id);
        if (food == null) {
            return ResponseEntity.notFound().build();
        }
        foodService.updateFood(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFood(@PathVariable Long id) {
        FoodEntity food = foodService.getFoodById(id);
        if (food == null) {
            return ResponseEntity.notFound().build();
        }
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<Object> deleteMultipleFood(String ids) {
        String[] idList = ids.split(",");
        for (String id : idList) {
            FoodEntity food = foodService.getFoodById(Long.parseLong(id));
            if (food == null) {
                return ResponseEntity.notFound().build();
            }
            foodService.deleteFood(Long.parseLong(id));
        }
        return ResponseEntity.noContent().build();
    }
}
