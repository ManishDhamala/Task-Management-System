package com.ingtech.taskmanagementsystem.service;

import com.ingtech.taskmanagementsystem.dto.TaskRequestDto;
import com.ingtech.taskmanagementsystem.dto.TaskResponseDto;
import com.ingtech.taskmanagementsystem.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    TaskResponseDto createTask(TaskRequestDto dto);

    Page<TaskResponseDto> getAllTasks(Pageable pageable);

    TaskResponseDto getTaskById(Long id);

    TaskResponseDto updateTask(Long id, TaskRequestDto dto);

    void deleteTask(Long id);

    List<TaskResponseDto> searchTaskByStatus(Status taskStatus);




}
