package com.chat.app.model.dto.user;

import lombok.Builder;

@Builder
public record UserInfoDTO(String id,
                          String userName,
                          String token,
                          String role) {
}
