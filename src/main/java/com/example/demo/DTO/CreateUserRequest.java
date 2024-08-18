package com.example.demo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateUserRequest {

    @NotNull(message = "Должен быть предоставлен email")
    @Email(message = "Должен быть введен корректный email-адрес в формате example@domain.com")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    private String userEmail;

    @NotNull(message = "Должен быть предоставлен пароль")
    @Size(min = 3, max = 255, message = "Пароль должен содержать не меньше 3 и не больше 255 символов")
    private String userPassword;

}
