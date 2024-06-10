package org.example.config;

import org.example.holidays.HolidaysProvider;
import org.example.holidays.nager.NagerClient;
import org.example.holidays.nager.NagerHolidaysProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.ZoneId;

@Component
public class MainConfig {

    // here we're using system clock, but instead a mechanism to create clock for each country or user should be introduced
    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.systemDefault());
    }

    @Bean
    public NagerClient nagerClient() {
        return new NagerClient();
    }

    @Bean
    public HolidaysProvider holidaysProvider(NagerClient nagerClient) {
        return new NagerHolidaysProvider(nagerClient);
    }
}
