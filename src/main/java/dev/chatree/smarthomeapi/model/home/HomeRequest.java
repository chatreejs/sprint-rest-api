package dev.chatree.smarthomeapi.model.home;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeRequest {
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String homePermission;
}
