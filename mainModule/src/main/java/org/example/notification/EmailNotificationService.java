package org.example.notification;


/**
 * Some abstraction to hide the notification part
 */
public interface EmailNotificationService {

    void scheduleNotification(NotificationPayload notification);
}
