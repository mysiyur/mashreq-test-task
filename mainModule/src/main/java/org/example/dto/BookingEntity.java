package org.example.dto;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("active_bookings")
@Builder(toBuilder = true)
public record BookingEntity(
        @Id UUID id,
        UUID roomId,
        @Column("booked_from") LocalDateTime bookedFrom,
        @Column("booked_until") LocalDateTime bookedUntil
) {
}
