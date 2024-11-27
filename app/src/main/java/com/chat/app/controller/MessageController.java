package com.chat.app.controller;

import com.chat.app.model.Message;
import com.chat.app.model.dto.message.MessageDTO;
import com.chat.app.model.dto.message.MessagePostDTO;
import com.chat.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> postMessage(@RequestBody MessagePostDTO content, UriComponentsBuilder uriComponentsBuilder){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Message message = messageService.postMessage(content, userName);
        URI url = uriComponentsBuilder.path("/messages/{id}").buildAndExpand(message.getId()).toUri();
        return ResponseEntity.created(url).body(message);
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getMessages(){
        List<MessageDTO> messageDTOS = messageService.getMessages();
        return ResponseEntity.ok(messageDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessage(@PathVariable("id") String id){
        MessageDTO messageDTO = messageService.getSingleMessage(id);
        return ResponseEntity.ok(messageDTO);
    }


}
