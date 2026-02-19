package com.ingtech.taskmanagementsystem.service;

import com.ingtech.taskmanagementsystem.dto.TaskRequestDto;
import com.ingtech.taskmanagementsystem.dto.TaskResponseDto;

public interface TaskService {

    TaskResponseDto createTask(TaskRequestDto dto);


}
