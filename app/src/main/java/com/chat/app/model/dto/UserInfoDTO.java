package com.chat.app.model.dto;

import lombok.Builder;

@Builder
public record UserInfoDTO(String id,
                          String userName,
                          String token,
                          String rol) {
}
