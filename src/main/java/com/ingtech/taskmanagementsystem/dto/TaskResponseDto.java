package com.ingtech.taskmanagementsystem.dto;

import com.ingtech.taskmanagementsystem.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {

    private Long id;

    private String title;

    private String description;

    private Status taskStatus;

    private LocalDate dueDate;

}
