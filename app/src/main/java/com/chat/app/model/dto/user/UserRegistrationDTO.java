package com.chat.app.model.dto.user;

import com.chat.app.validation.ValidRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegistrationDTO {

    @NotEmpty(message = "Username can not be empty")
    String username;

    @NotEmpty(message = "Password can not be null nor empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;

    @NotEmpty(message = "Rol can not be empty")
    String role;

}
