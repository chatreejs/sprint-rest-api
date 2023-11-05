package dev.chatree.smarthomeapi.model.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {
    private long id;
    private String name;
    private Double quantity;
    private Double maxQuantity;
    private String unit;
    private InventoryStatus status;
}
