package com.chat.app.service;

import com.chat.app.model.UserDocument;
import com.chat.app.model.dto.user.UserInfoDTO;
import com.chat.app.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserInfoDTO> getUsers(){
        return userRepository.findAll().stream()
                .map(this::fromUserToDTO).toList();
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }


    private UserInfoDTO fromUserToDTO(UserDocument userDocument){
        return UserInfoDTO.builder()
                .id(userDocument.getUserId())
                .userName(userDocument.getUsername())
                .role(String.valueOf(userDocument.getUserRole()))
                .build();
    }
}
