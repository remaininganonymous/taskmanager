package com.example.demo.DTO;

import com.example.demo.models.enums.Priority;
import com.example.demo.models.enums.State;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTaskDto {

    @NotNull(message = "Имя обязательно для ввода")
    @Size(max = 100, message = "Заголовок задачи не должен превышать 100 символов")
    private String taskTitle;

    @NotNull(message = "Задача должна иметь описание")
    @Size(max = 2048, message = "Описание не должно превышать 2048 символов")
    private String taskDescription;

    // TODO: валидатор (но также может быть передано и пустое значение)
    private Priority taskPriority;

    // TODO: валидатор (но также может быть передано и пустое значение)
    private State taskState;

}
