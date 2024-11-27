package com.chat.app.model.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(@NotBlank
                               String userName,
                               @NotBlank
                               String password) {
}
