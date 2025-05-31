package org.bookStore.composition.exception;

import org.bookStore.composition.exception.custom.AddCartItemException;
import org.bookStore.composition.exception.custom.BookNotFoundException;
import org.bookStore.composition.exception.custom.BookUpdateException;
import org.bookStore.composition.exception.custom.InsufficientBookStockException;
import org.bookStore.composition.response.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(BookNotFoundException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(InsufficientBookStockException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(InsufficientBookStockException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(BAD_REQUEST).body(apiResponse);
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

    @ExceptionHandler(AddCartItemException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(AddCartItemException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(BookUpdateException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(BookUpdateException exp) {
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
