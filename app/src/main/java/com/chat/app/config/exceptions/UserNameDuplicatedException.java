package com.chat.app.config.exceptions;

public class UserNameDuplicatedException extends RuntimeException{

    public UserNameDuplicatedException(String message) {
        super(message);
    }
}
