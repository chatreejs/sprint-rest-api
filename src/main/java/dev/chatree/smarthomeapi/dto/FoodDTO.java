package dev.chatree.smarthomeapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class FoodDTO {
    private String name;

    private Double quantity;

    private String unit;

    private Date buyDate;

    private Date expiryDate;
}
