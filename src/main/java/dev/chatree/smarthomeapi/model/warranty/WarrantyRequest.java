package dev.chatree.smarthomeapi.model.warranty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarrantyRequest {
    private String brand;
    private String productName;
    private String productNumber;
    private String model;
    private String serialNumber;
    private String purchaseDate;
    private String warrantyDate;
}
