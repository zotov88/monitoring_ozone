package monitoring_ozone.service.notifications;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class TelegramNotificationProxy implements NotificationProxy {

    private final TelegramBot bot;
    private final long chatID;

    public TelegramNotificationProxy(@Value("${telegram-bot.token}") String botToken,
                                     @Value("${telegram-bot.chat-id}") Long chatID
    ) {
        this.bot = new TelegramBot(botToken);
        this.chatID = chatID;
    }

    @Override
    public void send(String message) {
        bot.execute(new SendMessage(chatID, message));
    }
}
