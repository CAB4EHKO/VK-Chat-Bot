# VK Chat Bot

## Обзор

VK Chat Bot - это простой, но эффективный чат-бот, предназначенный для интеграции с ВКонтакте (VK), популярной российской социальной сетью. Этот проект использует Java 17, Spring Boot и REST API для обработки входящих сообщений, ответа на взаимодействия пользователей и обработки событий через механизм Long Polling VK.

Бот выполняет базовые операции, такие как получение сообщений и их эхо-ответ пользователям, что делает его отличной отправной точкой для дальнейшей кастомизации и разработки.

## Особенности

- **Интеграция Long Polling**: Использует Long Poll Server VK для обработки входящих событий и обработки сообщений пользователей в реальном времени.
- **Приложение Spring Boot**: Использует фреймворк Spring Boot для быстрой разработки и масштабируемости.
- **Функциональность эхо-ответа**: Бот может отвечать эхо-сообщениями пользователей, демонстрируя, как обрабатывать взаимодействия.
- **Конфигурация REST API**: Поддерживает VK API через использование RestTemplate Spring для отправки запросов и получения ответов.
- **Покрытие тестами**: Предоставлены юнит-тесты для критически важных функций, чтобы обеспечить надежную работу.

## Структура проекта

Проект организован следующим образом:

```
chat-bot/
 ├── src/
 │   ├── main/
 │   │   ├── java/chat/bot/chatbot/
 │   │   │   ├── config/                # Конфигурационные классы для VK API
 │   │   │   ├── controller/            # Обработка callback от VK
 │   │   │   ├── model/                 # Модели данных для Long Poll сервера
 │   │   │   ├── service/               # Сервисные классы для обработки запросов VK API
 │   ├── resources/
 │       ├── application.properties     # Конфигурационные свойства для интеграции с VK
 ├── pom.xml                            # Зависимости проекта и конфигурации сборки
```

## Начало работы

### Предварительные требования

- **Java 17**: Убедитесь, что у вас установлена Java 17.
- **Maven**: Этот проект использует Maven для управления зависимостями.
- **VK Developer Account**: Вам понадобится аккаунт разработчика VK и токен доступа для интеграции.

### Установка

1. Клонируйте репозиторий:

   ```sh
   git clone https://github.com/yourusername/vk-chat-bot.git
   cd vk-chat-bot
   ```

2. Настройте параметры VK API в `application.properties`:

   ```properties
   vk.api.token=YOUR_ACCESS_TOKEN
   vk.api.version=5.199
   vk.api.base-url=https://api.vk.com/method/
   ```

3. Соберите проект с помощью Maven:

   ```sh
   mvn clean install
   ```

4. Запустите приложение:

   ```sh
   mvn spring-boot:run
   ```

### Использование

Бот слушает входящие события от Long Poll сервера VK. Как только сообщение получено, он отправляет его обратно пользователю, демонстрируя, как использовать методы VK API, такие как `messages.send`.

## Тестирование

Юнит-тесты предоставлены в классе `VkApiServiceTest`, чтобы убедиться, что основные функции бота работают должным образом. Тесты охватывают:

- Получение информации о Long Poll сервере.
- Отправка сообщений пользователям.

Запустите тесты с помощью:

```sh
mvn test
```

## Используемые технологии

- **Java 17**: Основной язык программирования.
- **Spring Boot**: Фреймворк для создания бэкенда.
- **VK API**: Интеграция с ВКонтакте.
- **Mockito & JUnit 5**: Для написания юнит-тестов и проверки функциональности.

## Вклад в проект

Мы приветствуем вклад в проект! Открывайте задачи или отправляйте pull request для новых функций, улучшений или исправления ошибок.

1. Сделайте форк репозитория.

2. Создайте ветку для своей функции:

   ```sh
   git checkout -b feature/YourFeature
   ```

3. Закоммитьте свои изменения:

   ```sh
   git commit -m 'Add some feature'
   ```

4. Отправьте изменения в ветку:

   ```sh
   git push origin feature/YourFeature
   ```

5. Откройте pull request.

## Лицензия

Этот проект лицензирован под лицензией MIT - подробнее см. файл [LICENSE](LICENSE).

## Контакты

Если у вас есть вопросы или нужна дополнительная помощь, свяжитесь со мной по [https\://t.me/Sav\_R\_A].

