package com.chat.app.service;

import com.chat.app.model.UserDocument;
import com.chat.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserDocument> getUsers(){
        return userRepository.findAll();
    }
}
