package com.ingtech.taskmanagementsystem.service;

import com.ingtech.taskmanagementsystem.dto.TaskRequestDto;
import com.ingtech.taskmanagementsystem.dto.TaskResponseDto;
import com.ingtech.taskmanagementsystem.exception.TaskNotFoundException;
import com.ingtech.taskmanagementsystem.mapper.TaskMapper;
import com.ingtech.taskmanagementsystem.model.Status;
import com.ingtech.taskmanagementsystem.model.Task;
import com.ingtech.taskmanagementsystem.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<TaskResponseDto> getAllTasks(Pageable pageable) {
        Page<TaskResponseDto> tasks = taskRepository.findAll(pageable)
                .map(task -> taskMapper.toDto(task));

        return tasks;
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

        Task updatedTask = taskRepository.save(task);

        return taskMapper.toDto(updatedTask);
    }

    @Transactional
    @Override
    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        taskRepository.delete(task);
    }


    @Transactional(readOnly = true)
    @Override
    public List<TaskResponseDto> searchTaskByStatus(Status taskStatus) {

        List<TaskResponseDto> tasks = taskRepository.findByTaskStatus(taskStatus)
                .stream()
                .map(task -> taskMapper.toDto(task))
                .collect(Collectors.toList());

        return tasks;
    }



}
