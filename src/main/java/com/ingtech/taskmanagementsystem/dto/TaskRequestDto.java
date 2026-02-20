package com.ingtech.taskmanagementsystem.dto;

import com.ingtech.taskmanagementsystem.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequestDto {

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title should not exceed 200 characters")
    private String title;

    @Size(max = 600, message = "Description should not exceed 600 characters" )
    private String description;

    private Status taskStatus;

    private LocalDate dueDate;

}
