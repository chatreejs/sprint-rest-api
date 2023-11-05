package dev.chatree.smarthomeapi.service;

import dev.chatree.smarthomeapi.entity.FoodEntity;
import dev.chatree.smarthomeapi.model.food.FoodRequest;
import dev.chatree.smarthomeapi.model.food.FoodResponse;
import dev.chatree.smarthomeapi.model.food.FoodStatus;
import dev.chatree.smarthomeapi.repository.FoodRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<FoodResponse> getAllFood() {
        List<FoodEntity> foodEntityList = foodRepository.findAll();
        log.info("Found {} items", foodEntityList.size());
        List<FoodResponse> foodResponseList = new ArrayList<>();

        for (FoodEntity foodEntity : foodEntityList) {
            FoodResponse foodResponse = generateFoodResponse(foodEntity);
            foodResponseList.add(foodResponse);
        }
        log.info("Get all food done!");
        return foodResponseList;
    }

    public FoodResponse getFoodById(Long id) {
        log.info("id: {}", id);
        FoodEntity foodEntity = foodRepository.findById(id).orElse(null);
        if (foodEntity == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Food not found");
        }

        FoodResponse foodResponse = generateFoodResponse(foodEntity);

        log.info("getFoodById done!");
        return foodResponse;
    }

    public void createFood(FoodRequest foodRequest) {
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setName(foodRequest.getName());
        foodEntity.setQuantity(foodRequest.getQuantity());
        foodEntity.setUnit(foodRequest.getUnit());
        foodEntity.setBuyDate(foodRequest.getBuyDate());
        foodEntity.setExpiryDate(foodRequest.getExpiryDate());

        foodRepository.save(foodEntity);
        log.info("createFood done!");
    }

    public void updateFood(Long id, FoodRequest foodRequest) {
        log.info("id: {}", id);
        FoodEntity foodEntity = foodRepository.findById(id).orElse(null);
        if (foodEntity == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Food not found");
        }
        foodEntity.setName(foodRequest.getName());
        foodEntity.setQuantity(foodRequest.getQuantity());
        foodEntity.setUnit(foodRequest.getUnit());
        foodEntity.setBuyDate(foodRequest.getBuyDate());
        foodEntity.setExpiryDate(foodRequest.getExpiryDate());

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

    private FoodResponse generateFoodResponse(FoodEntity foodEntity) {
        FoodResponse foodResponse = new FoodResponse();
        foodResponse.setId(foodEntity.getId());
        foodResponse.setName(foodEntity.getName());
        foodResponse.setQuantity(foodEntity.getQuantity());
        foodResponse.setUnit(foodEntity.getUnit());
        foodResponse.setBuyDate(foodEntity.getBuyDate().toString());
        foodResponse.setExpiryDate(foodEntity.getExpiryDate().toString());
        foodResponse.setStatus(checkFoodStatus(foodEntity.getExpiryDate()));

        return foodResponse;
    }

    private FoodStatus checkFoodStatus(Date expiryDate) {
        if (expiryDate.before(new Date())) {
            return FoodStatus.EXPIRED;
        } else {
            return FoodStatus.FRESH;
        }
    }
}
