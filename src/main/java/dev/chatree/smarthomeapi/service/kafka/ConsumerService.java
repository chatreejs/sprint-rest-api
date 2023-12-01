package dev.chatree.smarthomeapi.service.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.chatree.smarthomeapi.model.kafka.SensorUpdate;
import dev.chatree.smarthomeapi.model.weathersensor.WeatherSensorResponse;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.TimeZone;

@Log4j2
@Service
public class ConsumerService extends AbstractConsumerSeekAware {

    @Value("${kafka.in.topics.weatherstation-sensor.key.data}")
    private String weatherStationSensorDataKey;

    @Resource
    private WeatherSensorResponse weatherSensorResponseCache;

    private final Gson gson = new GsonBuilder().serializeNulls().create();

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ConsumerService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @KafkaListener(
            topics = "${kafka.in.topics.weatherstation-sensor.topic}",
            groupId = "${kafka.consumer.group-id-prefix}-smarthome${kafka.config.environment}")
    public void consumeWeatherStationSensorMessage(@Header(KafkaHeaders.RECEIVED_KEY) String key,
                                                   @Header("__TypeId__") String type,
                                                   @Header(KafkaHeaders.OFFSET) Long offset,
                                                   @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp,
                                                   @Payload String value) {
        if (key.equalsIgnoreCase(weatherStationSensorDataKey)) {
            OffsetDateTime offsetDateTime;

            if (type.equals("SensorUpdate")) {
                log.info("type = {}", type);
                log.info("key = {}", key);
                log.info("offset = {}", offset);
                offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
                log.info("timestamp = {}", offsetDateTime);
                log.info("value = {}", value);
            } else {
                return;
            }

            if (type.equals("SensorUpdate")) {
                try {
                    SensorUpdate sensorUpdate = gson.fromJson(value, SensorUpdate.class);
                    WeatherSensorResponse weatherSensorResponse = weatherSensorReponseWrapper(offsetDateTime, sensorUpdate);
                    weatherSensorResponseCache = weatherSensorResponse;

                    simpMessagingTemplate.convertAndSend("/topic/weather-sensor", weatherSensorResponse);
                } catch (Exception e) {
                    log.error("Consume SensorUpdate error: ", e);
                }
            }
        }
    }

    private WeatherSensorResponse weatherSensorReponseWrapper(OffsetDateTime offsetDateTime, SensorUpdate sensorUpdate) {
        WeatherSensorResponse weatherSensorResponse = weatherSensorResponseCache;
        weatherSensorResponse.setTimestamp(offsetDateTime.toString());

        if (sensorUpdate.getType().equals("temperature")) {
            weatherSensorResponse.setTemperature(sensorUpdate.getValue());
        }
        if (sensorUpdate.getType().equals("humidity")) {
            weatherSensorResponse.setHumidity(sensorUpdate.getValue());
        }
        if (sensorUpdate.getType().equals("pressure")) {
            weatherSensorResponse.setPressure(sensorUpdate.getValue());
        }
        return weatherSensorResponse;
    }
}
