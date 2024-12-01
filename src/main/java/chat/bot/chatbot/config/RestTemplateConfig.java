package chat.bot.chatbot.config;

import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Конфигурация для создания RestTemplate Bean.
 * RestTemplate используется для выполнения HTTP-запросов к VK API.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

