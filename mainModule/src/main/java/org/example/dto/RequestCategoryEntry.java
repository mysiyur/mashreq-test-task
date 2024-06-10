package org.example.dto;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Builder(toBuilder = true)
@Table("request_category")
public record RequestCategoryEntry(
        @Id UUID id,
        String name
) {
    public boolean isNotWorkRemotely() {
        return !"WORK_REMOTELY".equals(name);
    }
}
