package dev.chatree.smarthomeapi.service;

import dev.chatree.smarthomeapi.dto.FoodDTO;
import dev.chatree.smarthomeapi.entity.FoodEntity;
import dev.chatree.smarthomeapi.repository.FoodRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<FoodEntity> getAllFood() {
        return foodRepository.findAll();
    }

    public FoodEntity getFoodById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    public void createFood(FoodDTO foodDTO) {
        FoodEntity food = new FoodEntity();
        food.setName(foodDTO.getName());
        food.setQuantity(foodDTO.getQuantity());
        food.setUnit(foodDTO.getUnit());
        food.setBuyDate(foodDTO.getBuyDate());
        food.setExpiryDate(foodDTO.getExpiryDate());

        foodRepository.save(food);
    }

    public void updateFood(Long id, FoodDTO foodDTO) {
        FoodEntity food = foodRepository.findById(id).orElse(null);
        if (food == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Food not found"
            );
        }
        food.setName(foodDTO.getName());
        food.setQuantity(foodDTO.getQuantity());
        food.setUnit(foodDTO.getUnit());
        food.setBuyDate(foodDTO.getBuyDate());
        food.setExpiryDate(foodDTO.getExpiryDate());

        foodRepository.save(food);
    }

    public void deleteFood(Long id) {
        FoodEntity food = foodRepository.findById(id).orElse(null);
        if (food == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Food not found"
            );
        }
        foodRepository.delete(food);
    }

}
