package com.chat.app.model;

import com.chat.app.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDocument {

    @Id
    String userId;

    @Field("username")
    String userName;

    String password;

    @Field("roles")
    List<UserRole> userRoleList;

}
