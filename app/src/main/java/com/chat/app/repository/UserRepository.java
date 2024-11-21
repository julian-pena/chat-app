package com.chat.app.repository;

import com.chat.app.model.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDocument, String> {
}
