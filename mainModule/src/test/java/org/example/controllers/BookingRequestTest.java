package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.util.JsonUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BookingRequestTest {

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void shouldBeJsonizable() throws JsonProcessingException {
        final var entry = new BookingRequest(
                10,
                LocalDateTime.parse("2024-08-04T10:00:00"),
                LocalDateTime.parse("2024-08-04T10:15:00")
        );

        final var json = """
                {
                    "capacity": 10,
                    "from": "2024-08-04T10:00:00",
                    "until": "2024-08-04T10:15:00"
                }
                """;

        final var value = JsonUtils.fromJson(json, BookingRequest.class);
        assertThat(value).isEqualTo(entry);
    }

}