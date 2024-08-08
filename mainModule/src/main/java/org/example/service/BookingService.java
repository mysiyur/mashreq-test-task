package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BookingEntity;
import org.example.dto.RoomEntity;
import org.example.repository.BookingsRepository;
import org.example.repository.RoomsRepository;
import org.example.util.TimeInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.time.Duration.between;
import static java.util.Comparator.comparing;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookingService {

    @Autowired
    private final RoomsRepository roomsRepository;

    @Autowired
    private final BookingsRepository bookingsRepository;

    @Autowired
    private final BookingTimeChecker bookingTimeChecker;

    @Transactional
    public void bookRoom(int desiredCapacity, LocalDateTime from, LocalDateTime until) {
        // TODO: don't like the idea of pessimistic locking but it gets the job done
        if (!bookingTimeChecker.isValidInterval(from, until)) {
            throw new IllegalArgumentException(String.format("Illegal time range %s - %s", from, until));
        }
        final var suitableRooms = roomsRepository.findSuitableRooms(desiredCapacity);
        final var occupiedIds = bookingsRepository.getOccupiedIds(from, until);
        final var availableRoom = suitableRooms.stream()
                .filter(it -> !occupiedIds.contains(it.id()))
                .min(comparing(RoomEntity::capacity));
        if (availableRoom.isEmpty()) {
            throw new RuntimeException();
        }
        final var bookingEntity = BookingEntity.builder()
                .roomId(availableRoom.get().id())
                .from(from)
                .until(until)
                .build();
        bookingsRepository.save(bookingEntity);
    }

    private static final List<LocalTime[]> BLOCKED_INTERVALS = List.of(
            new LocalTime[] {LocalTime.of(9, 0), LocalTime.of(9, 15)},
            new LocalTime[] {LocalTime.of(13, 0), LocalTime.of(13, 15)},
            new LocalTime[] {LocalTime.of(17, 0), LocalTime.of(17, 15)}
    );

    private static void checkTimeInterval(LocalDateTime from, LocalDateTime until) {
        // this is just an optimisation as if it is so - then forbidden time definitely intersects
        if (between(from, until).toDays() >= 1) {
            throw new IllegalArgumentException("Invalid period passed");
        }
        final var intervalToBook = new TimeInterval(from, until);
        checkIfIntersects(from, intervalToBook);
        checkIfIntersects(until, intervalToBook);
    }

    private static void checkIfIntersects(LocalDateTime from, TimeInterval intervalToBook) {
        BLOCKED_INTERVALS.forEach(it -> {
            final var forbiddenInterval = new TimeInterval(from.toLocalDate().atTime(it[0]), from.toLocalDate().atTime(it[1]));
            if (intervalToBook.intersects(forbiddenInterval)) {
                throw new IllegalArgumentException("Invalid period passed");
            }
        });
    }

    public List<RoomEntity> getFreeRooms(LocalDateTime from, LocalDateTime until) {
        final var freeRoomIds = bookingsRepository.getFreeRooms(from, until);
        return roomsRepository.findAllById(freeRoomIds);
    }
}
