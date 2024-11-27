package com.chat.app.model.dto.message;

import com.chat.app.model.Message;
import lombok.Builder;

@Builder
public record MessageDTO(String sender,
                         String content,
                         String timestamp) {
}
