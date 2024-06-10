package org.example.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.UUID;

public record TimeOffRequest(
        UUID userId,
        LocalDate from,
        LocalDate to,
        UUID requestCategoryId
) {

}
