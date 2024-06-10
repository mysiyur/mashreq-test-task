package org.example.holidays.nager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class NagerClientTest {

    @Test
    public void shouldWork() {
        final var nagerClient = new NagerClient();
        final var holidays = nagerClient.getHolidays("RU");
        System.out.println(holidays);
    }
}