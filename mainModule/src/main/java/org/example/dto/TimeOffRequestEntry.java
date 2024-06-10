package org.example.dto;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
@Table("time_off_request")
public record TimeOffRequestEntry(
        @Id UUID id,
        UUID requestCategoryId,
        UUID employeeId,
        LocalDate startDate,
        LocalDate endDate
) {}
