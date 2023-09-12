package monitoring_ozone.service.notifications;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class SenderNotifications {

    private final List<NotificationProxy> notifiers;

    public SenderNotifications(List<NotificationProxy> notifiers) {
        this.notifiers = notifiers;

    }

    public void sendAll(String message) {
        for (NotificationProxy notifier : notifiers) {
            notifier.send(message);
        }
    }
}
