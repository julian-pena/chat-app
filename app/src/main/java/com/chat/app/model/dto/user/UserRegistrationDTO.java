package com.chat.app.model.dto.user;

import com.chat.app.validation.ValidRole;
import jakarta.validation.constraints.NotEmpty;

public record UserRegistrationDTO(@NotEmpty(message = "Username can not be empty")
                                  String username,

                                  @NotEmpty(message = "Password can not be null nor empty")
                                  String password,

                                  @NotEmpty(message = "Rol can not be empty")
                                  @ValidRole String role) {
}
