package org.example.controllers;

import java.util.UUID;

public record FreeRoomResponse(
        UUID id,
        int capacity,
        String name
) {
}
