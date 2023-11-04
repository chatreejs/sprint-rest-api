package dev.chatree.smarthomeapi.service;

import dev.chatree.smarthomeapi.entity.FoodEntity;
import dev.chatree.smarthomeapi.model.FoodDTO;
import dev.chatree.smarthomeapi.repository.FoodRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
        List<FoodEntity> foodEntityList = foodRepository.findAll();
        log.info("Found {} items", foodEntityList.size());
        log.info("Get all food done!");
        return foodEntityList;
    }

    public FoodEntity getFoodById(Long id) {
        log.info("id: {}", id);
        FoodEntity food = foodRepository.findById(id).orElse(null);
        if (food == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Food not found");
        }
        log.info("getFoodById done!");
        return food;
    }

    public void createFood(FoodDTO foodDTO) {
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setName(foodDTO.getName());
        foodEntity.setQuantity(foodDTO.getQuantity());
        foodEntity.setUnit(foodDTO.getUnit());
        foodEntity.setBuyDate(foodDTO.getBuyDate());
        foodEntity.setExpiryDate(foodDTO.getExpiryDate());

        foodRepository.save(foodEntity);
        log.info("createFood done!");
    }

    public void updateFood(Long id, FoodDTO foodDTO) {
        log.info("id: {}", id);
        FoodEntity foodEntity = foodRepository.findById(id).orElse(null);
        if (foodEntity == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Food not found");
        }
        foodEntity.setName(foodDTO.getName());
        foodEntity.setQuantity(foodDTO.getQuantity());
        foodEntity.setUnit(foodDTO.getUnit());
        foodEntity.setBuyDate(foodDTO.getBuyDate());
        foodEntity.setExpiryDate(foodDTO.getExpiryDate());

        foodRepository.save(foodEntity);
        log.info("updateFood done!");
    }

    public void deleteFood(Long id) {
        log.info("id: {}", id);
        FoodEntity foodEntity = foodRepository.findById(id).orElse(null);
        if (foodEntity == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Food not found");
        }
        foodRepository.delete(foodEntity);
        log.info("deleteFood done!");
    }

    public void deleteMultipleFood(List<Long> ids) {
        log.info("ids: {}", ids);
        foodRepository.deleteAllById(ids);
        log.info("deleteMultipleFood done!");
    }
}
