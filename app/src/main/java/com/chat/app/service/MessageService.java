package com.chat.app.service;

import com.chat.app.model.Message;
import com.chat.app.model.dto.MessageDTO;
import com.chat.app.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessages(){
        return messageRepository.findAll();
    }

    public MessageDTO postMessage(String content, String userName){

        return null;
    }

}
