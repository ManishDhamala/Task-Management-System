package com.ingtech.taskmanagementsystem.service;

import com.ingtech.taskmanagementsystem.dto.TaskRequestDto;
import com.ingtech.taskmanagementsystem.dto.TaskResponseDto;
import com.ingtech.taskmanagementsystem.mapper.TaskMapper;
import com.ingtech.taskmanagementsystem.model.Status;
import com.ingtech.taskmanagementsystem.model.Task;
import com.ingtech.taskmanagementsystem.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;


    @Override
    public TaskResponseDto createTask(TaskRequestDto dto) {

        LocalDate today = LocalDate.now();

        // IF due date is not null and before current date it will throw an exception
        if (dto.getDueDate() != null && dto.getDueDate().isBefore(today)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Due date must be today or in future. You provided: "+dto.getDueDate());
        }

        // If no task status is provided, the task status will be PENDING
        if (dto.getTaskStatus() == null) {
            dto.setTaskStatus(Status.PENDING);
        } else {
            dto.setTaskStatus(dto.getTaskStatus());
        }

        Task task = taskMapper.toEntity(dto);

        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }
}
