package com.example.demo.DTO.request;

import com.example.demo.models.enums.Priority;
import com.example.demo.models.enums.State;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequest {

    @NotNull(message = "Необходимо указать ID изменяемой задачи")
    @Min(value = 1, message = "ID не должен быть меньше 1")
    private UUID taskId;

    @NotNull(message = "Имя обязательно для ввода")
    @Size(max = 100, message = "Заголовок задачи не должен превышать 100 символов")
    private String taskTitle;

    @NotNull(message = "Задача должна иметь описание")
    @Size(max = 2048, message = "Описание не должно превышать 2048 символов")
    private String taskDescription;

    // TODO: валидатор (но также может быть передано и пустое значение)
    private State taskState;

    // TODO: валидатор (но также может быть передано и пустое значение)
    private Priority taskPriority;

    @Min(value = 1, message = "ID испольнителя не должен быть меньше 1")
    private UUID taskExecutorId;

}