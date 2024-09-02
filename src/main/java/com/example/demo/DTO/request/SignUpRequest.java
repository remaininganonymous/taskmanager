package com.example.demo.DTO.request;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String password;
    private String confirmPassword;
}
