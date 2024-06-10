package org.example.notification;

import java.util.Map;
import java.util.UUID;

public record NotificationPayload(
        UUID id,
        EmailNotificationTemplate template,
        Map<String, String> parameters
) {}
