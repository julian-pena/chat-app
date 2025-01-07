package com.chat.app.controller;

import com.chat.app.model.dto.message.MessageDTO;
import com.chat.app.model.dto.message.MessagePostDTO;
import com.chat.app.model.dto.user.UserInfoDTO;
import com.chat.app.model.enums.MessageType;
import com.chat.app.service.ActiveUsersService;
import com.chat.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ChatController {

    private final MessageService messageService;

    private final ActiveUsersService activeUsersService;

    private final SimpMessageSendingOperations messagingTemplate;


    @Autowired
    public ChatController(MessageService messageService, ActiveUsersService activeUsersService, SimpMessageSendingOperations messagingTemplate) {
        this.messageService = messageService;
        this.activeUsersService = activeUsersService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/chat/home")
    public String getChatPage(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

            model.addAttribute("isAdmin", isAdmin);
        } else {
            model.addAttribute("username", "Anonymous");
            model.addAttribute("isAdmin", false);
        }
        return "messages";
    }


    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageDTO sendMessage(@Payload MessagePostDTO messagePostDTO, Principal principal){

        if (messagePostDTO.content().trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty or contain only whitespace");
        }

        MessageDTO messageDTO = MessageDTO.builder()
                .content(messagePostDTO.content())
                .sender(principal.getName())
                .timestamp(java.time.LocalDateTime.now().toString())
                .type(MessageType.CHAT)
                .build();

        // Persist
        messageService.postMessage(messageDTO);

        return messageDTO;

    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/messages")
    public MessageDTO addUser(@Payload UserInfoDTO userInfoDTO,
                              SimpMessageHeaderAccessor headerAccessor) {
        // Almacenar el nombre del usuario en la sesión WebSocket
        headerAccessor.getSessionAttributes().put("username", userInfoDTO.userName());

        // Actualizar lista de usuarios activos
        Set<String> activeUsers = activeUsersService.addUser(userInfoDTO.userName());

        // Enviar lista actualizada de usuarios
        messagingTemplate.convertAndSend("/topic/users", new ArrayList<>(activeUsers));

        return MessageDTO.builder()
                .content(userInfoDTO.userName() + " joined the chat!")
                .type(MessageType.JOIN)
                .sender("System")
                .timestamp(java.time.LocalDateTime.now().toString())
                .build();
    }

    // Agregar este nuevo endpoint
    @MessageMapping("/chat.getUsers")
    @SendTo("/topic/users")
    public List<String> getActiveUsers() {
        return new ArrayList<>(activeUsersService.getActiveUsers());
    }


}
