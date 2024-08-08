package org.example.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("rooms")
public record RoomEntity(
        @Id
        UUID id,
        int capacity,
        String name
) {
}
