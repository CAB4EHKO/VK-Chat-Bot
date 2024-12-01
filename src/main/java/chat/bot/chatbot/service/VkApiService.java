package chat.bot.chatbot.service;

import chat.bot.chatbot.config.VkApiConfig;
import chat.bot.chatbot.model.LongPollServer;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис для взаимодействия с VK API.
 * Содержит методы для отправки сообщений и работы с Long Poll сервером.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VkApiService {

    private final RestTemplate restTemplate;
    private final VkApiConfig vkApiConfig;

    /**
     * Отправляет сообщение пользователю VK.
     *
     * @param userId  идентификатор пользователя, которому необходимо отправить сообщение
     * @param message текст сообщения для отправки
     */
    public void sendMessage(String userId, String message) {
        URI uri = UriComponentsBuilder.fromUriString(vkApiConfig.getBaseUrl() + "messages.send")
                .queryParam("access_token", vkApiConfig.getToken())
                .queryParam("v", vkApiConfig.getVersion())
                .queryParam("user_id", userId)
                .queryParam("message", "Вы сказали: " + message)
                .queryParam("random_id", System.currentTimeMillis())
                .build().toUri();

        log.info("Отправка сообщения пользователю {}: {}", userId, message);
        restTemplate.postForObject(uri, null, String.class);
    }

    /**
     * Получает информацию о Long Poll сервере VK для группы.
     *
     * @return объект LongPollServer, содержащий данные о сервере
     */
    public LongPollServer getLongPollServer() {
        URI uri = UriComponentsBuilder.fromUriString(vkApiConfig.getBaseUrl() + "groups.getLongPollServer")
                .queryParam("access_token", vkApiConfig.getToken())
                .queryParam("v", vkApiConfig.getVersion())
                .queryParam("group_id", "228457667")
                .build().toUri();

        log.info("Запрос данных о Long Poll сервере");
        Map<String, Object> response = restTemplate.getForObject(uri, HashMap.class);
        if (response != null && response.containsKey("response")) {
            Map<String, Object> serverInfo = (Map<String, Object>) response.get("response");
            LongPollServer longPollServer = new LongPollServer();
            longPollServer.setKey((String) serverInfo.get("key"));
            longPollServer.setServer((String) serverInfo.get("server"));
            longPollServer.setTs((String) serverInfo.get("ts"));
            log.info("Получены данные о Long Poll сервере: key={}, server={}, ts={}", longPollServer.getKey(), longPollServer.getServer(), longPollServer.getTs());
            return longPollServer;
        }
        log.warn("Не удалось получить данные о Long Poll сервере");
        return null;
    }

    /**
     * Запускает процесс обработки событий через Long Poll сервер VK.
     *
     * @throws RuntimeException если не удалось получить данные о Long Poll сервере
     */
    public void longPollProcessing() {
        LongPollServer longPollServer = getLongPollServer();
        if (longPollServer == null) {
            throw new RuntimeException("Не удалось получить данные о Long Poll сервере.");
        }

        while (true) {
            try {
                URI uri = UriComponentsBuilder.fromUriString(longPollServer.getServer())
                        .queryParam("act", "a_check")
                        .queryParam("key", longPollServer.getKey())
                        .queryParam("ts", longPollServer.getTs())
                        .queryParam("wait", 2)
                        .queryParam("mode", 2)
                        .build().toUri();

                log.debug("Отправка запроса на Long Poll сервер: {}", uri);
                Map<String, Object> response = restTemplate.getForObject(uri, HashMap.class);
                if (response != null) {
                    if (response.containsKey("ts")) {
                        longPollServer.setTs((String) response.get("ts"));
                    }

                    if (response.containsKey("updates")) {
                        List<Map<String, Object>> updates = (List<Map<String, Object>>) response.get("updates");
                        for (Map<String, Object> update : updates) {
                            handleUpdate(update);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Ошибка при обработке Long Poll: {}", e.getMessage(), e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    /**
     * Обрабатывает полученное обновление от Long Poll сервера.
     *
     * @param update данные обновления, полученные от Long Poll сервера
     */
    private void handleUpdate(Map<String, Object> update) {
        String type = (String) update.get("type");
        log.info("Обработка обновления: тип - {}", type);
        if ("message_new".equals(type)) {
            Map<String, Object> object = (Map<String, Object>) update.get("object");
            if (object != null) {
                Map<String, Object> message = (Map<String, Object>) object.get("message");
                if (message != null) {
                    String userId = String.valueOf(message.get("from_id"));
                    String messageText = (String) message.get("text");
                    log.info("Новое сообщение от пользователя {}: {}", userId, messageText);
                    sendMessage(userId, messageText);
                }
            }
        }
    }
}

