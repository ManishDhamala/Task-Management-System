package com.ingtech.taskmanagementsystem.dto;

import com.ingtech.taskmanagementsystem.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskResponseDto {

    private Long id;

    private String title;

    private String description;

    private Status taskStatus;

    private LocalDate dueDate;

}
