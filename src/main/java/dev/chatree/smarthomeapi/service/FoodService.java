package dev.chatree.smarthomeapi.service;

import dev.chatree.smarthomeapi.model.Food;
import dev.chatree.smarthomeapi.entity.FoodEntity;
import dev.chatree.smarthomeapi.repository.FoodRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Log4j2
@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<FoodEntity> getAllFood() {
        log.info("Get all food");
        return foodRepository.findAll();
    }

    public FoodEntity getFoodById(Long id) {
        log.info("id: {}", id);
        FoodEntity food = foodRepository.findById(id).orElse(null);
        log.info("getFoodById done!");
        return foodRepository.findById(id).orElse(null);
    }

    public void createFood(Food foodDTO) {
        FoodEntity food = new FoodEntity();
        food.setName(foodDTO.getName());
        food.setQuantity(foodDTO.getQuantity());
        food.setUnit(foodDTO.getUnit());
        food.setBuyDate(foodDTO.getBuyDate());
        food.setExpiryDate(foodDTO.getExpiryDate());

        foodRepository.save(food);
        log.info("createFood done!");
    }

    public void updateFood(Long id, Food foodDTO) {
        log.info("id: {}", id);
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
        log.info("updateFood done!");
    }

    public void deleteFood(Long id) {
        log.info("id: {}", id);
        FoodEntity food = foodRepository.findById(id).orElse(null);
        if (food == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Food not found"
            );
        }
        foodRepository.delete(food);
        log.info("deleteFood done!");
    }

    public void deleteMultipleFood(List<Long> ids) {
        log.info("ids: {}", ids);
        foodRepository.deleteAllById(ids);
        log.info("deleteMultipleFood done!");
    }

}
