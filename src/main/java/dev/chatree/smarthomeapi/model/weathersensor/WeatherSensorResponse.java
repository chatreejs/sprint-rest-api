package dev.chatree.smarthomeapi.model.weathersensor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherSensorResponse {
    private String timestamp;
    private Double temperature;
    private Double humidity;
    private Double pressure;
}
