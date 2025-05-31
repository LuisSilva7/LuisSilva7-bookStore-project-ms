package org.bookStore.book.exception;

import org.bookStore.book.exception.custom.*;
import org.bookStore.book.response.ApiResponse;
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

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(CategoryNotFoundException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(SubCategoryNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(SubCategoryNotFoundException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(AuthorNotFoundException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(BookAlreadyExistsException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(CategoryAlreadyExistsException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(SubCategoryAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(SubCategoryAlreadyExistsException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(AuthorAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(AuthorAlreadyExistsException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(CONFLICT).body(apiResponse);
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}
