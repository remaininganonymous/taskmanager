package com.example.demo.controllers.v1;

import com.example.demo.DTO.request.JwtRequest;
import com.example.demo.DTO.request.SignUpRequest;
import com.example.demo.DTO.response.JwtResponse;
import com.example.demo.exceptions.ConfirmPasswordMismatchException;
import com.example.demo.services.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.error.ErrorResponseBuilder.buildErrorResponse;

@RequiredArgsConstructor
@RestController
@Tag(name = "Аутентификация")
public class AuthController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest request) {
        try {
            return ResponseEntity.ok(new JwtResponse(authService.createAuthToken(request)));
        } catch (AuthenticationException e) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            return buildErrorResponse(e, status);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody SignUpRequest request) {
        try {
            return ResponseEntity.ok(authService.createNewUser(request));
        } catch (ConfirmPasswordMismatchException e) {
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
            return buildErrorResponse(e, status);
        } catch (EntityExistsException e) {
            HttpStatus status = HttpStatus.CONFLICT;
            return buildErrorResponse(e, status);
        }
    }

}