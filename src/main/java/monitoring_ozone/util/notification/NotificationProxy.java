package monitoring_ozone.util.notification;

import monitoring_ozone.model.User;

public interface NotificationProxy {

    void send(User user, String message);

    void sendToAdmin(String message);
}
