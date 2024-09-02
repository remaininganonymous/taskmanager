package com.example.demo.DTO.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateCommentRequest {

    @NotNull(message = "Необходимо указать ID изменяемого комментария")
    @Min(value = 1, message = "ID не должен быть меньше 1")
    private UUID commentId;

    @Setter
    @NotNull(message = "Должен быть предоставлен текст комментария")
    @Size(max = 1024, message = "Превышен допустимый размер комментария (1024 символов)")
    private String commentText;

}