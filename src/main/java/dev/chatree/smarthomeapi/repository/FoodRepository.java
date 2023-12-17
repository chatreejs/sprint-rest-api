package dev.chatree.smarthomeapi.repository;

import dev.chatree.smarthomeapi.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
    List<FoodEntity> findAllByOrderByExpiryDateAsc();
}
