package dev.chatree.smarthomeapi.model.home;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeRequest {
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String homePermission;
}
