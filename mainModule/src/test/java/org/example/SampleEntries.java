package org.example;

import org.example.dto.EmployeeEntry;
import org.example.util.Email;

import java.util.Random;

import static java.util.UUID.randomUUID;

public class SampleEntries {

    private static final Random random = new Random();

    public static EmployeeEntry.EmployeeEntryBuilder newEmployee() {
        return EmployeeEntry.builder()
                .name("name" + random.nextInt(1000))
                .position("position" + random.nextInt(1000))
                .country("UAE")
                .email(Email.from("email" + randomUUID() + "@gmail.com"))
                .salary(random.nextFloat(2000F));
    }
}
