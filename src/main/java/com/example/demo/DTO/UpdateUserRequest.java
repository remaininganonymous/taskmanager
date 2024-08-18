package com.example.demo.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateUserRequest {

    @NotNull(message = "Необходимо указать ID изменяемого пользователя")
    @Min(value = 1, message = "ID  не должен быть меньше 1")
    private UUID userId;

    @NotNull(message = "Должен быть предоставлен email")
    @Email(message = "Должен быть введен корректный email-адрес в формате example@domain.com")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    private String userEmail;

    @NotNull(message = "Должен быть предоставлен пароль")
    @Size(min = 3, max = 255, message = "Пароль должен содержать не меньше 3 и не больше 255 символов")
    private String userPassword;

}