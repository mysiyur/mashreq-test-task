package org.example.dto;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestCategoryRepository extends ListCrudRepository<RequestCategoryEntry, UUID> {

    Optional<RequestCategoryEntry> findByName(String name);
}
