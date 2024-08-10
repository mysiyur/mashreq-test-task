package org.example.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookingController {

    @Autowired
    private final BookingService bookingService;

    private static final String timePattern = "yyyyMMddHHmm";

    @PostMapping("/book")
    public void book(@RequestBody BookingRequest bookingRequest) {
        bookingService.bookRoom(bookingRequest.capacity(), bookingRequest.from(), bookingRequest.until());
    }

    @GetMapping("/booking/{bookedFrom}/{bookedUntil}")
    @ResponseBody
    public FreeRoomsResponse getBookings(@PathVariable("bookedFrom") String fromRaw, @PathVariable("bookedUntil") String untilRaw) {
        final var pattern = DateTimeFormatter.ofPattern(timePattern);
        final var from = LocalDateTime.parse(fromRaw, pattern);
        final var until = LocalDateTime.parse(untilRaw, pattern);
        log.info("Returning booking bookedFrom {} bookedUntil {}", from, until);
        final var freeRooms = bookingService.getFreeRooms(from, until);
        final var freeRoomsResponse = freeRooms.stream().map(it -> new FreeRoomResponse(it.id(), it.capacity(), it.name())).toList();
        return new FreeRoomsResponse(freeRoomsResponse);
    }
}
