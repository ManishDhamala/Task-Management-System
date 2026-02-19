package com.ingtech.taskmanagementsystem.dto;

import com.ingtech.taskmanagementsystem.model.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequestDto {

    @NotBlank
    private String title;

    private String description;

    private Status taskStatus;

    private LocalDate dueDate;

}
