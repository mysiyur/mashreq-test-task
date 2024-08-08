package org.example.repository;

import org.example.dto.BookingEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface BookingsRepository extends ListCrudRepository<BookingEntity, UUID> {

    @Query("SELECT DISTINCT room_id FROM bookings WHERE NOT (from >= :until or until <= :from)")
    Set<UUID> getOccupiedIds(LocalDateTime from, LocalDateTime until);

    @Query("""
        SELECT r.id FROM rooms as r WHERE r.id NOT IN
            (
                SELECT DISTINCT room_id FROM bookings
                WHERE NOT (from >= :until or until <= :from)
            )
    """)
    List<UUID> getFreeRooms(LocalDateTime from, LocalDateTime until);
}
