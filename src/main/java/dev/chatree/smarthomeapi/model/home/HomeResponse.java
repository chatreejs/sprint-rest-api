package dev.chatree.smarthomeapi.model.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeResponse {
    private long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private Boolean isOwner;
    private String createDate;
    private String updateDate;
}
