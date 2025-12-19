package com.trackmyfix.trackmyfix.configs;

import com.trackmyfix.trackmyfix.configs.data.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner loadData(
            UserDataLoader userDataLoader,
            OrderDataLoader orderDataLoader,
            DeviceDataLoader deviceDataLoader,
            MovementDataLoader movementDataLoader,
            UserChangeDataLoader userChangeDataLoader) {
        return args -> {
            userDataLoader.load();
            /*
             * orderDataLoader.load();
             * deviceDataLoader.load();
             * movementDataLoader.load();
             * userChangeDataLoader.load();
             */
        };
    }
}
