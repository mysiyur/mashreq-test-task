package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class BookingController {

    @Autowired
    private final BookingService bookingService;

    @PostMapping("/book")
    public void book(@RequestBody BookingRequest bookingRequest) {
        bookingService.bookRoom(bookingRequest.capacity(), bookingRequest.from(), bookingRequest.until());
    }

    @GetMapping("/booking/${from}/${until}")
    @ResponseBody
    public FreeRoomsResponse getBookings(@PathVariable("from") String from, @PathVariable("until") String until) {
        final var freeRooms = bookingService.getFreeRooms(LocalDateTime.now(), LocalDateTime.now());
        final var freeRoomsResponse = freeRooms.stream().map(it -> new FreeRoomResponse(it.id(), it.capacity(), it.name())).toList();
        return new FreeRoomsResponse(freeRoomsResponse);
    }
}
