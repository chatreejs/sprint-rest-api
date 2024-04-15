package dev.chatree.smarthomeapi.model.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequest {
    private String name;
    private String brand;
    private Double quantity;
    private String unit;
    private String buyDate;
    private String expiryDate;
}
