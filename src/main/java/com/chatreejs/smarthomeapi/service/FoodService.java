package com.chatreejs.smarthomeapi.service;

import com.chatreejs.smarthomeapi.bean.request.FoodRequest;
import com.chatreejs.smarthomeapi.domain.Food;
import com.chatreejs.smarthomeapi.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }

    public Food getFoodById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    public void createFood(FoodRequest request) {
        Food food = new Food();
        food.setName(request.getName());
        food.setQuantity(request.getQuantity());
        food.setUnit(request.getUnit());
        food.setBuyDate(request.getBuyDate());
        food.setExpireDate(request.getExpireDate());

        foodRepository.save(food);
    }

    public void updateFood(Long id, FoodRequest request) {
        Food food = foodRepository.findById(id).orElse(null);
        if (food == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Food not found"
            );
        }
        food.setName(request.getName());
        food.setQuantity(request.getQuantity());
        food.setUnit(request.getUnit());
        food.setBuyDate(request.getBuyDate());
        food.setExpireDate(request.getExpireDate());

        foodRepository.save(food);
    }

    public void deleteFood(Long id) {
        Food food = foodRepository.findById(id).orElse(null);
        if (food == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Food not found"
            );
        }
        foodRepository.delete(food);
    }
}
