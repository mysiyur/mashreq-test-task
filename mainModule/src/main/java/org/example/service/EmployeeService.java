package org.example.service;

import lombok.AllArgsConstructor;
import org.example.controllers.NotFoundException;
import org.example.dto.EmployeeEntry;
import org.example.dto.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@Service
@AllArgsConstructor
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public UUID create(EmployeeEntry employeeEntry) {
        checkNotNull(employeeEntry, "employeeEntry");
        checkState(employeeEntry.id() == null, "id is not null");
        employeeRepository.save(employeeEntry);
        final var entry = employeeRepository.findByEmail(employeeEntry.email());
        return entry.get().id();
    }

    public Optional<EmployeeEntry> findById(UUID id) {
        // check for null
        return employeeRepository.findById(id);
    }

    public EmployeeEntry getById(UUID id) {
        final var entryO = findById(id);
        if (entryO.isEmpty()) {
            throw new NotFoundException("Can't find user");
        }
        return entryO.get();
    }

    public List<EmployeeEntry> getAll() {
        final var result = new ArrayList<EmployeeEntry>();
        employeeRepository.findAll().iterator().forEachRemaining(result::add);
        return result;
    }

    public void updateEntry(EmployeeEntry employeeEntry) {
        checkNotNull(employeeEntry, "employeeEntry");
        checkState(employeeEntry.id() != null, "id is null");
        employeeRepository.save(employeeEntry);
    }
}
