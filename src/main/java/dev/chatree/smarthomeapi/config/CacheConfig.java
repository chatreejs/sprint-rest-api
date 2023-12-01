package dev.chatree.smarthomeapi.config;

import dev.chatree.smarthomeapi.model.weathersensor.WeatherSensorResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {
    @Bean
    public WeatherSensorResponse weatherSensorResponseCache() {
        return new WeatherSensorResponse();
    }
}
