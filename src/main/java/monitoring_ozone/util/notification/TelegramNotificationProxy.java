package monitoring_ozone.util.notification;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import monitoring_ozone.model.User;
import org.springframework.stereotype.Component;

@Component
public final class TelegramNotificationProxy implements NotificationProxy {

    @Override
    public void send(User user, String message) {
        TelegramBot bot = new TelegramBot(user.getTgBotToken());
        bot.execute(new SendMessage(user.getTgChatId(), message));
    }

    @Override
    public void sendToAdmin(String message) {
        TelegramBot bot = new TelegramBot("token");
        bot.execute(new SendMessage(123456789L, message));
    }
}
