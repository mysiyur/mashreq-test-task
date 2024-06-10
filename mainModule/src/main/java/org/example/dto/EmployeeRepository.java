package org.example.dto;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeEntry, UUID> {

    Optional<EmployeeEntry> findByEmail(String email);

    List<EmployeeEntry> findByCountry(String country);

    @Query("SELECT * FROM employee WHERE id = :id LIMIT 1 FOR UPDATE")
    Optional<EmployeeEntry> findByIdForUpdate(@Param("id") UUID id);
}
