package org.example.service;

import org.example.util.TimeInterval;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.time.Duration.between;

public class BookingTimeChecker {

    private static final List<LocalTime[]> BLOCKED_INTERVALS = List.of(
            new LocalTime[] {LocalTime.of(9, 0), LocalTime.of(9, 15) },
            new LocalTime[] {LocalTime.of(13, 0), LocalTime.of(13, 15)},
            new LocalTime[] {LocalTime.of(17, 0), LocalTime.of(17, 15)}
    );

    public boolean isValidInterval(LocalDateTime from, LocalDateTime until) {
        // this is just an optimisation as if it is so - then forbidden time definitely intersects
        if (between(from, until).toDays() >= 1) {
            return false;
        }
        final var intervalToBook = new TimeInterval(from, until);
        return isAllowed(from, intervalToBook) && isAllowed(until, intervalToBook);
    }

    private static boolean isAllowed(LocalDateTime from, TimeInterval intervalToBook) {
        return BLOCKED_INTERVALS.stream().noneMatch(it -> {
            final var forbiddenInterval = new TimeInterval(from.toLocalDate().atTime(it[0]), from.toLocalDate().atTime(it[1]));
            return intervalToBook.intersects(forbiddenInterval);
        });
    }
}
