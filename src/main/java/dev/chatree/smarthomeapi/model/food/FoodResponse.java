package dev.chatree.smarthomeapi.model.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponse {
    private long id;
    private String name;
    private String brand;
    private Double quantity;
    private String unit;
    private String buyDate;
    private String expiryDate;
    private FoodStatus status;
}
