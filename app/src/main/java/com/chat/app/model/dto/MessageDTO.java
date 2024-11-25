package com.chat.app.model.dto;

public record MessageDTO(String sender,
                         String content,
                         String timestamp) {
}
