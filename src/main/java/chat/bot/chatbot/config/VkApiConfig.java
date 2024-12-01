package chat.bot.chatbot.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

/**
 * Конфигурация для свойств VK API.
 * Загружает параметры конфигурации из файла application.properties с префиксом "vk.api".
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "vk.api")
public class VkApiConfig {

    /**
     * Токен доступа к VK API.
     */
    private String token;

    /**
     * Версия VK API.
     */
    private String version;

    /**
     * Базовый URL для VK API.
     */
    private String baseUrl;
}
