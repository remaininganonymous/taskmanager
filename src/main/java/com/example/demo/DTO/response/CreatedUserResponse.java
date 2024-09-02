package com.example.demo.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreatedUserResponse {
    private UUID id;
    private String email;
}