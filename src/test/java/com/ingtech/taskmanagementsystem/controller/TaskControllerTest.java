package com.ingtech.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingtech.taskmanagementsystem.dto.TaskRequestDto;
import com.ingtech.taskmanagementsystem.dto.TaskResponseDto;
import com.ingtech.taskmanagementsystem.model.Status;
import com.ingtech.taskmanagementsystem.model.Task;
import com.ingtech.taskmanagementsystem.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void createTask_validRequest_returnsTaskResponseDto() throws Exception {

        when(taskService.createTask(any(TaskRequestDto.class))).thenReturn(responseDto1);

        mockMvc.perform(post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(responseDto1.getId()))
                .andExpect(jsonPath("$.title").value(responseDto1.getTitle()))
                .andExpect(jsonPath("$.description").value(responseDto1.getDescription()))
                .andExpect(jsonPath("$.taskStatus").value(responseDto1.getTaskStatus().name()))
                .andExpect(jsonPath("$.dueDate").value(responseDto1.getDueDate().toString()));

        verify(taskService, times(1)).createTask(any(TaskRequestDto.class));

    }

    @Test
    void getAllTasks_returnsPageOfTaskResponseDto() throws Exception {

        Page<TaskResponseDto> taskPage = new PageImpl<>(List.of(responseDto1, responseDto2),
                PageRequest.of(0, 3),
                2
        );

        when(taskService.getAllTasks(PageRequest.of(0, 3))).thenReturn(taskPage);

        mockMvc.perform(get("/api/v1/task")
                        .param("page", "0")
                        .param("size", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].id").value(responseDto1.getId()))
                .andExpect(jsonPath("$.content[0].title").value(responseDto1.getTitle()))
                .andExpect(jsonPath("$.content[0].description").value(responseDto1.getDescription()))
                .andExpect(jsonPath("$.content[0].taskStatus").value(responseDto1.getTaskStatus().name()))
                .andExpect(jsonPath("$.content[0].dueDate").value(responseDto1.getDueDate().toString()))
                .andExpect(jsonPath("$.page.totalElements").value(2))
                .andExpect(jsonPath("$.page.totalPages").value(1));

        verify(taskService, times(1)).getAllTasks(PageRequest.of(0, 3));

    }

    @Test
    void getTaskById_returnsTaskResponseDto() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(responseDto1);

        mockMvc.perform(get("/api/v1/task/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto1.getId()))
                .andExpect(jsonPath("$.title").value(responseDto1.getTitle()))
                .andExpect(jsonPath("$.description").value(responseDto1.getDescription()))
                .andExpect(jsonPath("$.taskStatus").value(responseDto1.getTaskStatus().name()))
                .andExpect(jsonPath("$.dueDate").value(responseDto1.getDueDate().toString()));

        verify(taskService, times(1)).getTaskById(1L);

    }







}