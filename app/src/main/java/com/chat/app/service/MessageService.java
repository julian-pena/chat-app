package com.chat.app.service;

import com.chat.app.model.Message;
import com.chat.app.model.UserDocument;
import com.chat.app.model.dto.message.MessageDTO;
import com.chat.app.repository.MessageRepository;
import com.chat.app.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<MessageDTO> getMessages(){
        List<Message> messages = messageRepository.findAll();

        return messages.stream()
                .map(this::fromMessageToDTO).toList();
    }


    public void postMessage(MessageDTO messageDTO){

        // Get userID
        String userId = userRepository.findByUsername(messageDTO.sender())
                .map(UserDocument::getUserId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        Message message = Message.builder()
                .content(messageDTO.content())
                .senderId(userId)
                .timeStamp(LocalDateTime.now())
                .build();

        // Update message with generated ID
        messageRepository.save(message);

    }

    public void deleteMessages(){
        messageRepository.deleteAll();
    }

    private MessageDTO fromMessageToDTO(Message message){

        UserDocument user = userRepository.findById(message.getSenderId())
                .orElseThrow( () -> new UsernameNotFoundException("No user with that ID"));

        return MessageDTO.builder()
                .content(message.getContent())
                .sender(user.getUsername())
                .timestamp(message.getTimeStamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }


}
