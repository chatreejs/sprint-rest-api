package dev.chatree.smarthomeapi.model.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequest {
    private String name;
    private Double quantity;
    private String unit;
    private Date buyDate;
    private Date expiryDate;
}
