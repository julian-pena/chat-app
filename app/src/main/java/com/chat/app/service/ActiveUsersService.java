package com.chat.app.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ActiveUsersService {
    private final Set<String> activeUsers = ConcurrentHashMap.newKeySet();

    public Set<String> addUser(String username) {
        if (username != null && !username.trim().isEmpty()) {
            activeUsers.add(username);
        }
        return activeUsers;
    }

    public Set<String> removeUser(String username) {
        if (username != null) {
            activeUsers.remove(username);
        }
        return activeUsers;
    }

    public Set<String> getActiveUsers() {
        return new HashSet<>(activeUsers);
    }
}
