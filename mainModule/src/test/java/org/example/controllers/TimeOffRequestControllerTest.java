package org.example.controllers;

import org.example.AbstractIntegrationTest;
import org.example.Main;
import org.example.dto.EmployeeEntry;
import org.example.dto.TimeOffRequestRepository;
import org.example.holidays.Holiday;
import org.example.holidays.HolidaysProvider;
import org.example.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.SampleEntries.newEmployee;
import static org.example.util.JsonUtil.listToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class TimeOffRequestControllerTest extends AbstractIntegrationTest {

    @Autowired
    private TimeOffRequestRepository timeOffRequestRepository;

    @MockBean
    private HolidaysProvider holidaysProvider;

    private EmployeeEntry user;

    @BeforeEach
    public void setUp() {
        user = createUser(newEmployee().build());
    }

    @Test
    public void getHolidays() {
        final var uri = basePath()
                .pathSegment("timeoff", "holidays", user.id().toString())
                .build(Map.of());

        final var now = LocalDate.now();
        final var serviceResponse = List.of(new Holiday(now.plusDays(2)), new Holiday(now.plusDays(4)));
        when(holidaysProvider.getHolidays(any(), eq(user.country()))).thenReturn(serviceResponse);

        final var response = sendGet(uri);
        assertThat(response.statusCode()).isEqualTo(200);

        final var holidays = listToJson(response.body(), Holiday.class);
        assertThat(holidays).isEqualTo(serviceResponse);
    }

    @Test
    public void shouldSubmitRequest() {
        final var uri = basePath()
                .pathSegment("timeoff", "request")
                .build(Map.of());


        final var from = LocalDate.now();
        final var until = from.plusDays(3);
        final var httpRequest = new TimeOffRequest(
                user.id(),
                from,
                until,
                this.annualLeaveId
        );
        final var response = sendPost(uri, JsonUtil.toString(httpRequest));
        assertThat(response.statusCode()).isEqualTo(200);

        final var requests = timeOffRequestRepository.findAllByEmployeeId(user.id());
        assertThat(requests).hasSize(1);

        final var timeOffRequests = requests.get(0);
        assertThat(timeOffRequests.employeeId()).isEqualTo(user.id());
        assertThat(timeOffRequests.startDate()).isEqualTo(from);
        assertThat(timeOffRequests.endDate()).isEqualTo(until);
        assertThat(timeOffRequests.requestCategoryId()).isEqualTo(timeOffRequests.requestCategoryId());
    }
}