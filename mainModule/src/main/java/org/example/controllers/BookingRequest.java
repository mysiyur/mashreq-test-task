package org.example.controllers;

import org.example.util.JsonDateTimeFormat;

import java.time.LocalDateTime;

public record BookingRequest(
        int capacity,
        @JsonDateTimeFormat LocalDateTime from,
        @JsonDateTimeFormat LocalDateTime until
) {
}
