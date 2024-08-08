package org.example.util;

import org.junit.jupiter.api.Test;

import static java.time.LocalDateTime.parse;
import static org.assertj.core.api.Assertions.assertThat;

class TimeIntervalTest {

    @Test
    public void shouldCheckIntersection() {
        final var subject = new TimeInterval(
                parse("2024-08-04T10:00:00"),
                parse("2024-08-04T10:15:00")
        );

        final var intersects1 = new TimeInterval(parse("2024-08-04T09:00:00"), parse("2024-08-04T10:10:00"));
        assertThat(subject.intersects(intersects1)).isTrue();
        final var intersects2 = new TimeInterval(parse("2024-08-04T10:10:00"), parse("2024-08-04T11:00:00"));
        assertThat(subject.intersects(intersects2)).isTrue();
        final var notIntersects1 = new TimeInterval(parse("2024-08-04T09:00:00"), parse("2024-08-04T09:30:00"));
        assertThat(subject.intersects(notIntersects1)).isFalse();
        final var notIntersects2 = new TimeInterval(parse("2024-08-04T10:30:00"), parse("2024-08-04T11:00:00"));
        assertThat(subject.intersects(notIntersects2)).isFalse();
        final var intersects3 = new TimeInterval(parse("2024-08-04T09:00:00"), parse("2024-08-04T11:00:00"));
        assertThat(subject.intersects(intersects3)).isTrue();
        final var intersects4 = new TimeInterval(parse("2024-08-04T10:01:00"), parse("2024-08-04T10:10:00"));
        assertThat(subject.intersects(intersects4)).isTrue();
    }

}