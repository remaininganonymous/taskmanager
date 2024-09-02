package com.example.demo.DTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LeaveCommentRequest {

    @Setter
    @NotNull(message = "Должен быть предоставлен ID комментария")
    private UUID taskId;

    @Setter
    @NotNull(message = "Должен быть предоставлен текст комментария")
    @Size(max = 1024, message = "Превышен допустимый размер комментария (1024 символов)")
    private String commentText;

}
