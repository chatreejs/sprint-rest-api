package dev.chatree.smarthomeapi.model.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponse {
    private long id;
    private String name;
    private Double quantity;
    private String unit;
    private String buyDate;
    private String expiryDate;
    private FoodStatus status;
}
