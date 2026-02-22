package com.ingtech.taskmanagementsystem.service;

import com.ingtech.taskmanagementsystem.dto.TaskRequestDto;
import com.ingtech.taskmanagementsystem.dto.TaskResponseDto;
import com.ingtech.taskmanagementsystem.exception.TaskNotFoundException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    private Task task1;

    private Task task2;

    private TaskResponseDto responseDto1;

    private TaskResponseDto responseDto2;

    @BeforeEach
    void setUp() {

        // Arrange - Test data
        requestDto = new TaskRequestDto();
        requestDto.setTitle("Test Task");
        requestDto.setDescription("Test Description");
        requestDto.setTaskStatus(Status.PENDING);
        requestDto.setDueDate(LocalDate.of(2026, 8, 30));

        task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Test Task");
        task1.setDescription("Test Description");
        task1.setTaskStatus(Status.PENDING);
        task1.setDueDate(LocalDate.of(2026, 8, 30));

        task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Test Task 2");
        task2.setDescription("Test Description 2");
        task2.setTaskStatus(Status.PENDING);
        task2.setDueDate(LocalDate.of(2026, 9, 30));


        responseDto1 = new TaskResponseDto();
        responseDto1.setId(1L);
        responseDto1.setTitle("Test Task");
        responseDto1.setDescription("Test Description");
        responseDto1.setTaskStatus(Status.PENDING);
        responseDto1.setDueDate(LocalDate.of(2026, 8, 30));

        responseDto2 = new TaskResponseDto();
        responseDto2.setId(2L);
        responseDto2.setTitle("Test Task 2");
        responseDto2.setDescription("Test Description 2");
        responseDto2.setTaskStatus(Status.PENDING);
        responseDto2.setDueDate(LocalDate.of(2026, 9, 30));

    }


    @Test
    void createTask_validRequest_returnsTaskResponseDto() {

        when(taskMapper.toEntity(requestDto)).thenReturn(task1);
        when(taskRepository.save(task1)).thenReturn(task1);
        when(taskMapper.toDto(task1)).thenReturn(responseDto1);

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
        verify(taskRepository, times(1)).save(task1);
        verify(taskMapper, times(1)).toDto(task1);

    }

    @Test
    void getAllTasks_validPageable_returnsPageOfTaskResponseDto() {

        Pageable pageable = PageRequest.of(0, 5);

        Page<Task> taskPage = new PageImpl<>(List.of(task1, task2), pageable, 2);

        when(taskRepository.findAll(pageable)).thenReturn(taskPage);
        when(taskMapper.toDto(task1)).thenReturn(responseDto1);
        when(taskMapper.toDto(task2)).thenReturn(responseDto2);

        //Act
        Page<TaskResponseDto> result = taskService.getAllTasks(pageable);

        //Assert

        // Pagination validation
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());

        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals("Test Task", result.getContent().get(0).getTitle());
        assertEquals("Test Description", result.getContent().get(0).getDescription());
        assertEquals(Status.PENDING, result.getContent().get(0).getTaskStatus());
        assertEquals(LocalDate.of(2026, 8, 30), result.getContent().get(0).getDueDate());

        assertEquals(2L, result.getContent().get(1).getId());
        assertEquals("Test Task 2", result.getContent().get(1).getTitle());
        assertEquals("Test Description 2", result.getContent().get(1).getDescription());
        assertEquals(Status.PENDING, result.getContent().get(1).getTaskStatus());
        assertEquals(LocalDate.of(2026, 9, 30), result.getContent().get(1).getDueDate());


        // Verify
        verify(taskRepository, times(1)).findAll(pageable);
        verify(taskMapper, times(1)).toDto(task1);
        verify(taskMapper, times(1)).toDto(task2);

    }


    @Test
    void getTaskById_returnsTaskResponseDto() {

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskMapper.toDto(task1)).thenReturn(responseDto1);

        // Act
        TaskResponseDto result = taskService.getTaskById(1L);

        //Assert
        assertNotNull(result);
        assertEquals(responseDto1.getId(), result.getId());
        assertEquals(responseDto1.getTitle(), result.getTitle());
        assertEquals(responseDto1.getDescription(), result.getDescription());
        assertEquals(responseDto1.getTaskStatus(), result.getTaskStatus());
        assertEquals(requestDto.getDueDate(), result.getDueDate());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskMapper, times(1)).toDto(task1);

    }


    @Test
    void getTaskById_nonExistingId_throwsTaskNotFoundException() {

        when(taskRepository.findById(15L)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(15L));

        assertEquals("Task not found with id: 15", exception.getMessage());

        verify(taskRepository, times(1)).findById(15L);
        verify(taskMapper, never()).toDto(any());  // verifies toDto method never executes when task is not found
    }


    @Test
    void updateTask_verifyFields_returnsUpdatedTaskResponseDto() {

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(task1)).thenReturn(task1);
        when(taskMapper.toDto(task1)).thenReturn(responseDto1);

        // Act
        TaskResponseDto result = taskService.updateTask(1L, requestDto);

        //Assert - fields are properly set as per the request
        assertEquals(requestDto.getTitle(), task1.getTitle());
        assertEquals(requestDto.getDescription(), task1.getDescription());
        assertEquals(requestDto.getTaskStatus(), task1.getTaskStatus());
        assertEquals(requestDto.getDueDate(), task1.getDueDate());

        assertNotNull(result);
        assertEquals(responseDto1.getId(), result.getId());
        assertEquals(responseDto1.getTitle(), result.getTitle());
        assertEquals(responseDto1.getDescription(), result.getDescription());
        assertEquals(responseDto1.getTaskStatus(), result.getTaskStatus());
        assertEquals(responseDto1.getDueDate(), result.getDueDate());


        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(task1);
        verify(taskMapper, times(1)).toDto(task1);
    }



}