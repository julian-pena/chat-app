package com.chat.app.controller;

import com.chat.app.model.dto.message.MessageDTO;
import com.chat.app.model.dto.user.UserInfoDTO;
import com.chat.app.service.MessageService;
import com.chat.app.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatHistoryController {

    private final MessageService messageService;

    private final UserService userService;

    public ChatHistoryController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @MessageMapping("/admin.fetchHistory")
    @SendTo("/topic/admin/history")
    public List<MessageDTO> fetchHistory() {
        return messageService.getMessages();
    }

    @MessageMapping("/admin.deleteMessages")
    @SendTo("/topic/admin/history")
    public List<MessageDTO> deleteMessages() {
        messageService.deleteMessages();
        return messageService.getMessages();
    }

    @MessageMapping("/admin.fetchUsers")
    @SendTo("/topic/admin/users")
    public List<UserInfoDTO> fetchUsers() {
        return userService.getUsers();
    }

    @MessageMapping("/admin.deleteUser")
    @SendTo("/topic/admin/users")
    public List<UserInfoDTO> deleteUser(String userId) {
        userService.deleteUser(userId);
        return userService.getUsers();
    }



}
