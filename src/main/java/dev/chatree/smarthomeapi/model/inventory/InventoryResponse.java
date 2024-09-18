package dev.chatree.smarthomeapi.model.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {
    private long id;
    private String name;
    private String brand;
    private Double quantity;
    private Double maxQuantity;
    private String unit;
    private InventoryStatus status;
    private String restockDate;
    private String updateBy;
    private String updateDate;
}
