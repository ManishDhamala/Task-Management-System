package com.ingtech.taskmanagementsystem.dto;

import com.ingtech.taskmanagementsystem.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDto {

    private Long id;

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Status taskStatus;

    private LocalDate dueDate;

}
