package org.example.holidays.nager;

import lombok.AllArgsConstructor;
import org.example.holidays.Holiday;
import org.example.holidays.HolidaysProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class NagerHolidaysProvider implements HolidaysProvider {

    @Autowired
    private NagerClient nagerClient;

    @Override
    public List<Holiday> getHolidays(LocalDate from, String countryCode) {
        // don't like the design - better to cache, we don't need that much of requests, holidays are not changing
        // also country should be internalised, there's no guarantee that it'll be shared between API and what we store in database
        final var holidays = nagerClient.getHolidays(countryCode);
        final var threshold = from.plusDays(7);
        final var comingHolidays = holidays.stream()
                .map(it -> LocalDate.parse(it.date()))
                .filter(it -> it.isBefore(threshold))
                .toList();
        return comingHolidays.stream().map(Holiday::new).toList();
    }
}
