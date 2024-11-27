package com.chat.app.model.dto.message;

import jakarta.validation.constraints.NotBlank;

public record MessagePostDTO(
        @NotBlank(message = "Not blank messages allowed") String content) {
}
