package com.ingtech.taskmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)  // Serialize page using a stable dto instead of pageImpl directly
public class TaskManagementSystemApplication {

    public static void main(String[] args) {

        SpringApplication.run(TaskManagementSystemApplication.class, args);

        System.out.println("Hello World !");
        System.out.println("The program has started");

    }

}
