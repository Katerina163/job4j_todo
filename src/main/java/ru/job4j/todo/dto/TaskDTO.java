package ru.job4j.todo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class TaskDTO {
    private int id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String created;
    private boolean done;
}
