package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LeaveCommentDto {

    @Setter
    @NotNull(message = "Должен быть предоставлен текст комментария")
    @Size(max = 1024, message = "Превышен допустимый размер комментария (1024 символов)")
    private String commentText;

}
