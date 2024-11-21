package com.chat.app.model.controller;

import com.chat.app.model.UserDocument;
import com.chat.app.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {


    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDocument> test(){
        return userService.getUsers();
    }
}
