package com.truongduchoang.SpringBootRESTfullAPIs.errors;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.truongduchoang.SpringBootRESTfullAPIs.models.ApiResponse;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException ex) {
        var result = new ApiResponse<>(HttpStatus.NOT_FOUND, ex.getMessage(), null, ex.getErrorCode());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateResource(DuplicateResourceException ex) {
        var result = new ApiResponse<>(HttpStatus.CONFLICT, ex.getMessage(), null, ex.getErrorCode());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(BadRequestException ex) {
        var result = new ApiResponse<>(HttpStatus.BAD_REQUEST, ex.getMessage(), null, ex.getErrorCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ApiResponse<?>> handleFileUpload(FileUploadException ex) {
        var result = new ApiResponse<>(HttpStatus.BAD_REQUEST, ex.getMessage(), null, ex.getErrorCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(NoSuchElementException ex) {
        var result = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Not found user", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> uniqueErrors = new LinkedHashMap<>();

        for (var error : ex.getBindingResult().getFieldErrors()) {
            uniqueErrors.putIfAbsent(error.getField(), error.getDefaultMessage());
        }

        String errors = String.join("\n", uniqueErrors.values());

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST,
                errors,
                null,
                "VALIDATION_ERROR");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Object>> handleBindExceptions(BindException ex) {
        Map<String, String> uniqueErrors = new LinkedHashMap<>();

        for (var error : ex.getBindingResult().getFieldErrors()) {
            uniqueErrors.putIfAbsent(error.getField(), error.getDefaultMessage());
        }

        String errors = String.join("\n", uniqueErrors.values());

        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST,
                errors,
                null,
                "VALIDATION_ERROR");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestPartException.class,
            HttpMediaTypeNotSupportedException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ApiResponse<?>> handleInvalidRequest(Exception ex) {
        var result = new ApiResponse<>(HttpStatus.BAD_REQUEST, ex.getMessage(), null, "BAD_REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAllException(Exception ex) {
        var result = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null,
                "INTERNAL_SERVER_ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}
