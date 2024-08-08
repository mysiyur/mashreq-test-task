package org.example.util;

import java.time.LocalDateTime;

public record TimeInterval(
        LocalDateTime from,
        LocalDateTime until
) {

    public boolean intersects(TimeInterval timeInterval) {
        return !(from.isAfter(timeInterval.until) || until.isBefore(timeInterval.from));
    }
}
