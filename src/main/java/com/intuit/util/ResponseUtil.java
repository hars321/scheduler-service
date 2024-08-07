package com.intuit.util;

import com.intuit.core.entity.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> successResponse(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>(true, data, message, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> successResponse(T data) {
        return successResponse(data, "Operation successful");
    }

    public static <T> ResponseEntity<ApiResponse<T>> successResponse() {
        return successResponse(null, "Operation successful");
    }

    public static <T> ResponseEntity<ApiResponse<T>> errorResponse(String message, String errorCode, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>(false, null, message, errorCode);
        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> errorResponse(String message, HttpStatus status) {
        return errorResponse(message, null, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> errorResponse(String message) {
        return errorResponse(message, HttpStatus.BAD_REQUEST);
    }
}
