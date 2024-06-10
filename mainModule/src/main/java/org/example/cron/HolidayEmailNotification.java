package org.example.cron;

import org.example.dto.EmployeeEntry;
import org.example.dto.EmployeeRepository;
import org.example.holidays.HolidaysProvider;
import org.example.notification.EmailNotificationService;
import org.example.notification.NotificationPayload;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Map;

import static org.example.notification.EmailNotificationTemplate.HOLIDAY_7_DAY_WARNING;

/**
 * This class is expected to be triggered by cron for each country daily.
 * The part about sending the update every day or only once when the new holiday
 * will be exactly 7 days from now is negotiable
 */
public class HolidayEmailNotification {

    @Autowired
    private HolidaysProvider holidaysProvider;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private EmployeeRepository employeeRepository;

    public void execute(LocalDate currentDate, String country) {
        final var holidays = holidaysProvider.getHolidays(currentDate, country);
        if (holidays.isEmpty()) {
            return;
        }
        final var employees = employeeRepository.findByCountry(country);
        // should be more transactional
        for (EmployeeEntry employee : employees) {
            emailNotificationService.scheduleNotification(new NotificationPayload(employee.id(), HOLIDAY_7_DAY_WARNING, Map.of()));
        }
    }
}
