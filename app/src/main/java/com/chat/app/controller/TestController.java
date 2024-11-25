package com.chat.app.controller;

import com.chat.app.model.Message;
import com.chat.app.model.UserDocument;
import com.chat.app.service.MessageService;
import com.chat.app.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {


    private final UserService userService;

    private final MessageService messageService;

    public TestController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping
    public List<UserDocument> test(){
        return userService.getUsers();
    }

    @GetMapping("/message")
    public List<Message> test1(){
        return messageService.getMessages();
    }


}
