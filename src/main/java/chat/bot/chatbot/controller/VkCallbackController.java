package chat.bot.chatbot.controller;

import chat.bot.chatbot.service.VkApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Контроллер для обработки обратных вызовов (callback) от VK.
 * Получает и обрабатывает события, отправляемые VK сервером.
 */
@RestController
@Slf4j
public class VkCallbackController {

    private final VkApiService vkApiService;

    /**
     * Конструктор для внедрения зависимости VkApiService.
     *
     * @param vkApiService сервис для взаимодействия с VK API
     */
    public VkCallbackController(VkApiService vkApiService) {
        this.vkApiService = vkApiService;
    }

    /**
     * Обрабатывает входящий запрос от VK.
     *
     * @param requestBody тело запроса, содержащего данные события VK
     * @return ответ для подтверждения получения события
     */
    @PostMapping("/callback")
    public String handleCallback(@RequestBody Map<String, Object> requestBody) {
        // Получаем значение типа события из тела запроса
        String type = (String) requestBody.get("type");
        log.info("Получен запрос: тип - {}", type);
        log.debug("Тело запроса: {}", requestBody);

        // Обработка события подтверждения
        if ("confirmation".equals(type)) {
            return "c28fd7ee"; // Замените на ключ подтверждения из VK
        }

        if ("message_new".equals(type)) {
            Map<String, Object> object = (Map<String, Object>) requestBody.get("object");
            if (object != null) {
                Map<String, Object> message = (Map<String, Object>) object.get("message");
                String userId = String.valueOf(message.get("from_id"));
                String messageText = (String) message.get("text");

                log.info("Сообщение от пользователя {}: {}", userId, messageText);

                vkApiService.sendMessage(userId, messageText);
            }
        }

        return "ok";
    }
}