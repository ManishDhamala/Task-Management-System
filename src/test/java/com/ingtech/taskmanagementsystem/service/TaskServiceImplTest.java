package com.ingtech.taskmanagementsystem.service;

import com.ingtech.taskmanagementsystem.dto.TaskRequestDto;
import com.ingtech.taskmanagementsystem.dto.TaskResponseDto;
import com.ingtech.taskmanagementsystem.mapper.TaskMapper;
import com.ingtech.taskmanagementsystem.model.Status;
import com.ingtech.taskmanagementsystem.model.Task;
import com.ingtech.taskmanagementsystem.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskRequestDto requestDto;

    private Task task;

    private TaskResponseDto responseDto;

    @BeforeEach
    void setUp() {

        // Arrange - Test data
        requestDto = new TaskRequestDto();
        requestDto.setTitle("Test Task");
        requestDto.setDescription("Test Description");
        requestDto.setTaskStatus(Status.PENDING);
        requestDto.setDueDate(LocalDate.of(2026, 8, 30));

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setTaskStatus(Status.PENDING);
        task.setDueDate(LocalDate.of(2026, 8, 30));

        responseDto = new TaskResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Test Task");
        responseDto.setDescription("Test Description");
        responseDto.setTaskStatus(Status.PENDING);
        responseDto.setDueDate(LocalDate.of(2026, 8, 30));

    }


    @Test
    void createTask_validRequest_returnsTaskResponseDto(){

        when(taskMapper.toEntity(requestDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(responseDto);

        // Act
        TaskResponseDto result = taskService.createTask(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(requestDto.getTitle(), result.getTitle());
        assertEquals(requestDto.getDescription(), result.getDescription());
        assertEquals(requestDto.getTaskStatus(), result.getTaskStatus());
        assertEquals(requestDto.getDueDate(), result.getDueDate());

        // Verify method calls
        verify(taskMapper, times(1)).toEntity(requestDto);
        verify(taskRepository,times(1)).save(task);
        verify(taskMapper, times(1)).toDto(task);

    }


}