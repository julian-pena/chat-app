package com.chat.app.config;

import com.chat.app.model.dto.message.MessageDTO;
import com.chat.app.model.enums.MessageType;
import com.chat.app.service.ActiveUsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {


    private final SimpMessageSendingOperations messagingTemplate;
    private final ActiveUsersService activeUsersService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null) {
            log.info("User Disconnected: " + username);

            // Actualizar lista de usuarios activos
            Set<String> activeUsers = activeUsersService.removeUser(username);

            // Enviar lista actualizada de usuarios
            messagingTemplate.convertAndSend("/topic/users", new ArrayList<>(activeUsers));

            // Mensaje de desconexión
            MessageDTO message = MessageDTO.builder()
                    .content(username + " has left the chat!")
                    .sender("System")
                    .timestamp(java.time.LocalDateTime.now().toString())
                    .type(MessageType.LEAVE)
                    .build();

            messagingTemplate.convertAndSend("/topic/messages", message);
        }
    }
}


