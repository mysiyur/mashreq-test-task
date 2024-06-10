package org.example.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.holidays.Holiday;
import org.example.holidays.HolidaysProvider;
import org.example.service.EmployeeService;
import org.example.service.TimeOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
public class TimeOffRequestController {

    @Autowired
    private Clock clock;

    @Autowired
    private HolidaysProvider holidaysProvider;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TimeOffService timeOffService;

    // this request should be authenticated as other users should not access this
    @GetMapping(value = "/timeoff/holidays/{userId}", produces = "application/json")
    @ResponseBody
    public List<Holiday> getHolidays(@PathVariable("userId") UUID userId) {
        final var currentDate = ZonedDateTime.ofInstant(clock.instant(), clock.getZone()).toLocalDate();
        final var employee = employeeService.getById(userId);
        log.info("Resolving holidays for user {} starting from {}", employee.id(), currentDate);
        return holidaysProvider.getHolidays(currentDate, employee.country());
    }

    @PostMapping(value = "/timeoff/request", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> requestTimeoff(@RequestBody TimeOffRequest timeOffRequest) {
        log.info("Processing time off request for user {}", timeOffRequest.userId());
        timeOffService.sendRequest(timeOffRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
