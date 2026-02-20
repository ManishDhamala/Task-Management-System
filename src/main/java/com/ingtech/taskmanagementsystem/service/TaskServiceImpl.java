package com.ingtech.taskmanagementsystem.service;

import com.ingtech.taskmanagementsystem.dto.TaskRequestDto;
import com.ingtech.taskmanagementsystem.dto.TaskResponseDto;
import com.ingtech.taskmanagementsystem.exception.TaskNotFoundException;
import com.ingtech.taskmanagementsystem.mapper.TaskMapper;
import com.ingtech.taskmanagementsystem.model.Task;
import com.ingtech.taskmanagementsystem.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    @Transactional
    @Override
    public TaskResponseDto createTask(TaskRequestDto dto) {

        Task task = taskMapper.toEntity(dto);

        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(task -> taskMapper.toDto(task))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public TaskResponseDto getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        return taskMapper.toDto(task);
    }


    @Transactional
    @Override
    public TaskResponseDto updateTask(Long id, TaskRequestDto dto) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setTaskStatus(dto.getTaskStatus());
        task.setDueDate(dto.getDueDate());

        taskRepository.save(task);

        return taskMapper.toDto(task);
    }


}
