package com.chat.app.model.dto.message;

import com.chat.app.model.Message;
import com.chat.app.model.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record MessageDTO(String sender,
                         String content,
                         String timestamp,
                         MessageType type
) {
}


