package monitoring_ozone.service.notifications;

import monitoring_ozone.model.User;

public interface NotificationProxy {

    void send(User user, String message);
}
