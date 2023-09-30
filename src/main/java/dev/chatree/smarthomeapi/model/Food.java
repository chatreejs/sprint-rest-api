package dev.chatree.smarthomeapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Food {
    private String name;

    private Double quantity;

    private String unit;

    private Date buyDate;

    private Date expiryDate;
}
