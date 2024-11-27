package com.chat.app.config;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exc) {
        Map<String, Object> responseBody = new HashMap<>();
        exc.getBindingResult().getFieldErrors().forEach(error ->
                responseBody.put(error.getField(), error.getDefaultMessage())
        );
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("timeStamp", currentTime());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException exc) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", exc.getMessage());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("timeStamp", currentTime());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException exc) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", exc.getMessage());
        responseBody.put("status", HttpStatus.UNAUTHORIZED.value());
        responseBody.put("timeStamp", currentTime());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(UsernameNotFoundException exc) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", exc.getMessage());
        responseBody.put("status", HttpStatus.UNAUTHORIZED.value());
        responseBody.put("timeStamp", currentTime());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EnumConstantNotPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleEnumConstantNotPresentException(EnumConstantNotPresentException exc){
        Map<String, Object> responseBody = new HashMap<>();
        String enumValues = Arrays.stream(exc.enumType().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.joining(", "));

        responseBody.put("message", "Invalid value for constant.");
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("timeStamp", currentTime());
        responseBody.put("Allowed values", enumValues);
        responseBody.put("rejected value", exc.constantName());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    // Handle exceptions when the argument passed in the controller URL is not valid
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exc){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Invalid parameter: " + exc.getValue());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("parameter", exc.getName());
        responseBody.put("timestamp", currentTime());
        responseBody.put("requiredType", Objects.requireNonNull(exc.getRequiredType()).getSimpleName());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }


    private String currentTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
