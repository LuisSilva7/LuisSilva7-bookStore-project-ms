package org.bookStore.user.exception;


import org.bookStore.user.exception.custom.CartCreationException;
import org.bookStore.user.exception.custom.InvalidCredentialsException;
import org.bookStore.user.exception.custom.UserAlreadyExistsException;
import org.bookStore.user.exception.custom.UserNotFoundException;
import org.bookStore.user.response.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(UserNotFoundException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(UserAlreadyExistsException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(InvalidCredentialsException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(UNAUTHORIZED).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ApiResponse<String> apiResponse = new ApiResponse<>(
                errorMessage, null);

        return ResponseEntity.status(BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(CartCreationException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(CartCreationException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}
