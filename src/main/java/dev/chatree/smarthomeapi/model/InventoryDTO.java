package dev.chatree.smarthomeapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    private String name;
    private Double quantity;
    private Double maxQuantity;
    private String unit;
}
