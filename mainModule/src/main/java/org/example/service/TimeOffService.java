package org.example.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controllers.TimeOffRequest;
import org.example.dto.EmployeeRepository;
import org.example.dto.RequestCategoryEntry;
import org.example.dto.RequestCategoryRepository;
import org.example.dto.TimeOffRequestEntry;
import org.example.dto.TimeOffRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TimeOffService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TimeOffRequestRepository timeOffRequestRepository;

    @Autowired
    private RequestCategoryRepository requestCategoryRepository;

    @Transactional
    public void sendRequest(TimeOffRequest timeOffRequest) {
        // this select is used as a distributed write lock
        employeeRepository.findByIdForUpdate(timeOffRequest.userId());
        log.info("Processing time off request for {} user", timeOffRequest.userId());

        final var allRequests = timeOffRequestRepository.findAllByEmployeeId(timeOffRequest.userId());
        log.info("Found {} time off requests", allRequests.size());
        final var categoryIds = allRequests.stream().map(TimeOffRequestEntry::requestCategoryId).collect(Collectors.toSet());
        final var requestCategories = requestCategoryRepository.findAllById(categoryIds).stream()
                .collect(Collectors.toMap(RequestCategoryEntry::id, Function.identity()));
        for (TimeOffRequestEntry request : allRequests) {
            if (isInRange(timeOffRequest.from(), request) || isInRange(timeOffRequest.to(), request)) {
                log.info("Time off request {} has dates intersection, checking eligibility", request.id());
                final var requestCategory = requestCategories.get(request.requestCategoryId());
                final var existingCategory = requestCategories.get(timeOffRequest.requestCategoryId());
                checkRequestTypes(requestCategory, existingCategory);
            }
        }
        log.info("Saving time off request for user {}", timeOffRequest.userId());
        timeOffRequestRepository.save(TimeOffRequestEntry.builder()
                .employeeId(timeOffRequest.userId())
                .requestCategoryId(timeOffRequest.requestCategoryId())
                .startDate(timeOffRequest.from())
                .endDate(timeOffRequest.to())
                .build());
    }

    private void checkRequestTypes(RequestCategoryEntry firstRequestCategory, RequestCategoryEntry secondRequestCategory) {
        if (firstRequestCategory.name().equals(secondRequestCategory.name())
                || (firstRequestCategory.isNotWorkRemotely() && secondRequestCategory.isNotWorkRemotely())) {
            throw new IllegalStateException("Can't request the same request type");
        }
    }

    private static boolean isInRange(LocalDate date, TimeOffRequestEntry timeOffRequestEntry) {
        return date.isBefore(timeOffRequestEntry.startDate()) || date.isAfter(timeOffRequestEntry.endDate());
    }
}
