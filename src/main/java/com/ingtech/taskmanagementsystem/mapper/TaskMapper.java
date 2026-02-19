package com.ingtech.taskmanagementsystem.mapper;

import com.ingtech.taskmanagementsystem.dto.TaskRequestDto;
import com.ingtech.taskmanagementsystem.dto.TaskResponseDto;
import com.ingtech.taskmanagementsystem.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskResponseDto toDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();

        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setTaskStatus(task.getTaskStatus());
        dto.setDueDate(task.getDueDate());

        return dto;
    }

    public Task toEntity(TaskRequestDto dto) {

        Task task = new Task();

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setTaskStatus(dto.getTaskStatus());
        task.setDueDate(dto.getDueDate());

        return task;

    }

}
