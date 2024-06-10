package org.example.service;

import org.example.AbstractIntegrationTest;
import org.example.dto.EmployeeEntry;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.SampleEntries.newEmployee;

class EmployeeServiceTest extends AbstractIntegrationTest {

    @Autowired
    EmployeeService employeeService;

    @Test
    public void shouldWrite() {
        final var id = employeeService.create(newEmployee().build());

        final var entry = employeeService.findById(id);
        assertThat(entry).isPresent();
    }

    @Test
    public void shouldUpdate() {
        final var entry = newEmployee().build();
        final var id = employeeService.create(entry);

        final var newEntry = newEmployee().id(id).build();
        employeeService.updateEntry(newEntry);

        final var updatedEntryO = employeeService.findById(id);
        assertThat(updatedEntryO).isPresent();
        final var updatedEntry = updatedEntryO.get();
        assertThat(updatedEntry.name()).isEqualTo(newEntry.name());
        assertThat(updatedEntry.position()).isEqualTo(newEntry.position());
        assertThat(updatedEntry.email()).isEqualTo(newEntry.email());
        assertThat(updatedEntry.salary()).isEqualTo(newEntry.salary());
    }

    @Test
    public void shouldListAll() {
        final var firstId = employeeService.create(newEmployee().build());
        final var secondId = employeeService.create(newEmployee().build());

        final var allEntries = employeeService.getAll();
        assertThat(allEntries).map(EmployeeEntry::id).contains(firstId, secondId);
    }
}