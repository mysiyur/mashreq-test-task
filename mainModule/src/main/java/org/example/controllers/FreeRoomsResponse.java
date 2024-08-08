package org.example.controllers;

import java.util.List;

public record FreeRoomsResponse(
        List<FreeRoomResponse> rooms
) {
}
