package dev.chatree.smarthomeapi;

import jakarta.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

@SpringBootApplication
public class SmarthomeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmarthomeApiApplication.class, args);
    }

}
