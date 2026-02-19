package com.ingtech.taskmanagementsystem.mapper;

import com.ingtech.taskmanagementsystem.dto.TaskDto;
import com.ingtech.taskmanagementsystem.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDto toDto(Task task) {

        TaskDto taskDto = new TaskDto();

        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setTaskStatus(task.getTaskStatus());
        taskDto.setDueDate(task.getDueDate());

        return taskDto;
    }


    public Task toEntity(TaskDto taskDto) {

        Task task = new Task();

        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setTaskStatus(taskDto.getTaskStatus());
        task.setDueDate(taskDto.getDueDate());

        return task;

    }


}
