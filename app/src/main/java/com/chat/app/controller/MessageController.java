package com.chat.app.controller;

import com.chat.app.model.dto.MessageDTO;
import com.chat.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<String> postMessage(@RequestBody String content){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderId = authentication.getName();
        System.out.println("\n\n" + senderId);
        MessageDTO messageDTO = messageService.postMessage(content, senderId);
        return ResponseEntity.ok("OK");
    }

}
