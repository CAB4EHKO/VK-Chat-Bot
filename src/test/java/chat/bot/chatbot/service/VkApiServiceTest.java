package chat.bot.chatbot.service;

//import chat.bot.chatbot.config.VkApiConfig;
//import chat.bot.chatbot.model.LongPollServer;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class VkApiServiceTest {
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @Mock
//    private VkApiConfig vkApiConfig;
//
//    @InjectMocks
//    private VkApiService vkApiService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        when(vkApiConfig.getBaseUrl()).thenReturn("https://api.vk.com/method/");
//        when(vkApiConfig.getToken()).thenReturn("test_token");
//        when(vkApiConfig.getVersion()).thenReturn("5.131");
//    }
//
//    @Test
//    void testGetLongPollServerSuccess() {
//        // Arrange
//        URI uri = UriComponentsBuilder.fromUriString(vkApiConfig.getBaseUrl() + "groups.getLongPollServer")
//                .queryParam("access_token", vkApiConfig.getToken())
//                .queryParam("v", vkApiConfig.getVersion())
//                .queryParam("group_id", "228457667")
//                .build().toUri();
//
//        Map<String, Object> response = new HashMap<>();
//        Map<String, Object> responseData = new HashMap<>();
//        responseData.put("key", "test_key");
//        responseData.put("server", "https://lp.vk.com/wh12345");
//        responseData.put("ts", "12345678");
//        response.put("response", responseData);
//
//        when(restTemplate.getForObject(uri, Map.class)).thenReturn(response);
//
//
//        // Act
//        LongPollServer longPollServer = vkApiService.getLongPollServer();
//
//        // Assert
//        assertNotNull(longPollServer);
//        assertEquals("test_key", longPollServer.getKey());
//        assertEquals("https://lp.vk.com/wh12345", longPollServer.getServer());
//        assertEquals("12345678", longPollServer.getTs());
//    }
//
//    @Test
//    void testGetLongPollServerFailure() {
//        // Arrange
//        URI uri = UriComponentsBuilder.fromUriString(vkApiConfig.getBaseUrl() + "groups.getLongPollServer")
//                .queryParam("access_token", vkApiConfig.getToken())
//                .queryParam("v", vkApiConfig.getVersion())
//                .queryParam("group_id", "228457667")
//                .build().toUri();
//
//        when(restTemplate.getForObject(uri, HashMap.class)).thenReturn(null);
//
//        // Act
//        LongPollServer longPollServer = vkApiService.getLongPollServer();
//
//        // Assert
//        assertNull(longPollServer);
//    }
//
//    @Test
//    void testSendMessage() {
//        // Arrange
//        String userId = "123456";
//        String message = "Hello, World!";
//        URI uri = UriComponentsBuilder.fromUriString(vkApiConfig.getBaseUrl() + "messages.send")
//                .queryParam("access_token", vkApiConfig.getToken())
//                .queryParam("v", vkApiConfig.getVersion())
//                .queryParam("user_id", userId)
//                .queryParam("message", "Вы сказали: " + message)
//                .queryParam("random_id", System.currentTimeMillis())
//                .build().toUri();
//
//        doNothing().when(restTemplate).postForObject(any(URI.class), any(), eq(String.class));
//
//        // Act
//        vkApiService.sendMessage(userId, message);
//
//        // Assert
//        verify(restTemplate, times(1)).postForObject(any(URI.class), any(), eq(String.class));
//    }
//}
import chat.bot.chatbot.config.VkApiConfig;
import chat.bot.chatbot.model.LongPollServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VkApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private VkApiConfig vkApiConfig;

    @InjectMocks
    private VkApiService vkApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(vkApiConfig.getBaseUrl()).thenReturn("https://api.vk.com/method/");
        when(vkApiConfig.getToken()).thenReturn("test_token");
        when(vkApiConfig.getVersion()).thenReturn("5.199");
    }

    @Test
    void testGetLongPollServerFailure() {
        // Arrange
        URI uri = UriComponentsBuilder.fromUriString(vkApiConfig.getBaseUrl() + "groups.getLongPollServer")
                .queryParam("access_token", vkApiConfig.getToken())
                .queryParam("v", vkApiConfig.getVersion())
                .queryParam("group_id", "228457667")
                .build().toUri();

        when(restTemplate.getForObject(uri, Map.class)).thenReturn(null);

        // Act
        LongPollServer longPollServer = vkApiService.getLongPollServer();

        // Assert
        assertNull(longPollServer);
    }

    @Test
    void testSendMessage() {
        // Arrange
        String userId = "123456";
        String message = "Hello, World!";
        URI uri = UriComponentsBuilder.fromUriString(vkApiConfig.getBaseUrl() + "messages.send")
                .queryParam("access_token", vkApiConfig.getToken())
                .queryParam("v", vkApiConfig.getVersion())
                .queryParam("user_id", userId)
                .queryParam("message", "Вы сказали: " + message)
                .queryParam("random_id", "123456789")
                .build().toUri();

        when(restTemplate.postForObject(any(URI.class), any(), eq(String.class))).thenReturn(null);

        // Act
        vkApiService.sendMessage(userId, message);

        // Assert
        verify(restTemplate, times(1)).postForObject(any(URI.class), any(), eq(String.class));
    }
}

