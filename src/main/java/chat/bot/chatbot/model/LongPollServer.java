package chat.bot.chatbot.model;

import lombok.*;

/**
 * Модель для представления данных Long Poll сервера VK.
 */
@Data
public class LongPollServer {

    /**
     * Ключ доступа к Long Poll серверу.
     */
    private String key;

    /**
     * Адрес Long Poll сервера.
     */
    private String server;

    /**
     * Параметр ts, используемый для получения обновлений.
     */
    private String ts;
}
