package com.chat.app.model;

import com.chat.app.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDocument {

    @Id
    String userId;

    @Field("username")
    @Indexed(unique = true)
    String username;

    String password;

    @Field("rol")
    UserRole userRole;

}
