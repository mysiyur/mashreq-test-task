package org.example.service;

import org.example.AbstractIntegrationTest;
import org.example.SampleEntries;
import org.example.controllers.TimeOffRequest;
import org.example.dto.EmployeeEntry;
import org.example.dto.TimeOffRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TimeOffServiceTest extends AbstractIntegrationTest {

    @Autowired
    private TimeOffService timeOffService;

    @Autowired
    private TimeOffRequestRepository timeOffRequestRepository;

    private EmployeeEntry user;

    @BeforeEach
    public void setUp() {
        user = createUser(SampleEntries.newEmployee().build());
    }

    @RepeatedTest(20)
    public void shouldBeConcurrent() {
        try (final var executorService = Executors.newFixedThreadPool(2)) {
            var currentDate = LocalDate.now();
            final var firstRequest = new TimeOffRequest(user.id(), currentDate, currentDate.plusDays(1), annualLeaveId);
            final var secondRequest = new TimeOffRequest(user.id(), currentDate.plusDays(1), currentDate.plusDays(2), annualLeaveId);
            final var firstFuture = runAsync(() -> timeOffService.sendRequest(firstRequest), executorService);
            final var secondFuture = runAsync(() -> timeOffService.sendRequest(secondRequest), executorService);
            final var combinedFuture = allOf(firstFuture, secondFuture);
            assertThrows(ExecutionException.class, combinedFuture::get);
        }
    }

    @Test
    public void shouldStoreRequest() {
        final var startDate = LocalDate.now();
        final var endDate = startDate.plusDays(2);
        timeOffService.sendRequest(new TimeOffRequest(user.id(), startDate, endDate, annualLeaveId));
        final var requests = timeOffRequestRepository.findAllByEmployeeId(user.id());

        assertThat(requests).hasSize(1);
        final var entry = requests.get(0);
        assertThat(entry.employeeId()).isEqualTo(user.id());
        assertThat(entry.startDate()).isEqualTo(startDate);
        assertThat(entry.endDate()).isEqualTo(endDate);
    }
}