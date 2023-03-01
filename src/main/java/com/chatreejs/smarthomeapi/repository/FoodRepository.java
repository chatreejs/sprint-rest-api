package com.chatreejs.smarthomeapi.repository;

import com.chatreejs.smarthomeapi.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
