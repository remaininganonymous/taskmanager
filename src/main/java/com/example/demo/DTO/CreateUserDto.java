package com.example.demo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateUserDto {

    @NotNull(message = "Поле для email пользователя должно быть заполнено")
    @Email(message = "Должен быть введен корректный email-адрес")
    private String userEmail;

    @NotNull(message = "Поле для пароля пользователя должно быть заполнено")
    @Size(min = 3, message = "Пароль должен содержать 3 и более символов")
    private String userPassword;

}
