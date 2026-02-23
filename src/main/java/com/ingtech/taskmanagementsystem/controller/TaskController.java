package com.ingtech.taskmanagementsystem.controller;

import com.ingtech.taskmanagementsystem.dto.TaskRequestDto;
import com.ingtech.taskmanagementsystem.dto.TaskResponseDto;
import com.ingtech.taskmanagementsystem.model.Status;
import com.ingtech.taskmanagementsystem.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/v1/task")
@Validated
@RequiredArgsConstructor
@RestController
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(dto));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {

        // Create pagination object with page number and size
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getAllTasks(pageable));

    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable @Positive Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable @Positive Long id, @Valid @RequestBody TaskRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.updateTask(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable @Positive Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();  // Returns 204 - No content
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDto>> searchTaskByStatus(@RequestParam Status status) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.searchTaskByStatus(status));
    }


}
