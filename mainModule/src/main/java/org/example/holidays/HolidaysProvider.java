package org.example.holidays;

import java.time.LocalDate;
import java.util.List;

public interface HolidaysProvider {

    List<Holiday> getHolidays(LocalDate from, String countryCode);
}
