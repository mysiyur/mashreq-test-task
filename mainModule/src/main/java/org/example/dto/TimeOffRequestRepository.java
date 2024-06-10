package org.example.dto;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimeOffRequestRepository extends ListCrudRepository<TimeOffRequestEntry, UUID> {

    List<TimeOffRequestEntry> findAllByEmployeeId(UUID employeeId);
}
