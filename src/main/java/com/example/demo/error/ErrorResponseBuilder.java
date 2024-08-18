package com.example.demo.error;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public final class ErrorResponseBuilder {

    public static ResponseEntity<?> buildErrorResponse(Exception e, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(status.value() + " " + status.getReasonPhrase() + ": " + e.getMessage());
    }

}
