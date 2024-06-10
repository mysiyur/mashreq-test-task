package org.example.dto;

import lombok.Builder;
import org.example.util.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Builder(toBuilder = true)
@Table("employee")
public record EmployeeEntry(
        @Id UUID id,
        String name,
        String position,
        String email,
        String country,
        float salary
){

    public static final class EmployeeEntryBuilder {
        String email;

        EmployeeEntryBuilder email(String email) {
            this.email = email;
            return this;
        }

        public EmployeeEntryBuilder email(Email email) {
            this.email = email.getEmail();
            return this;
        }
    }
}
