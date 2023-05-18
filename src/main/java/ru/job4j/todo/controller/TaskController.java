package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.dto.TaskDTO;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class TaskController {
    private TaskService service;

    public TaskController(TaskService simpleTaskService) {
        service = simpleTaskService;
    }

    @GetMapping({"/", "/all"})
    public String getAll(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "list";
    }

    @GetMapping("/new")
    public String getAllNew(Model model) {
        model.addAttribute("tasks", service.findAllNew());
        return "list";
    }

    @GetMapping("/done")
    public String getAllDone(Model model) {
        model.addAttribute("tasks", service.findAllDone());
        return "list";
    }

    @GetMapping("/task/{id}")
    public String getTaskById(@PathVariable int id, Model model) {
        Optional<TaskDTO> task = service.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Неправильно указан номер задачи");
            return "error";
        }
        model.addAttribute("task", task.get());
        return "task";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable int id, Model model) {
        var isDeleted = service.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Не удалось удалить");
            return "error";
        }
        return "redirect:/all";
    }

    @GetMapping("/modify/{id}")
    public String getModifyPage(@PathVariable int id, HttpServletResponse response, Model model) {
        response.addCookie(new Cookie("id", Integer.toString(id)));
        Optional<TaskDTO> task = service.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Неправильно указан номер задачи");
            return "error";
        }
        model.addAttribute("task", task.get());
        return "modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute TaskDTO task, @CookieValue(value = "id") String id, Model model) {
        task.setId(Integer.parseInt(id));
        try {
            var isUpdated = service.update(task);
            if (!isUpdated) {
                model.addAttribute("message", "Ой");
                return "error";
            }
        } catch (NumberFormatException nfe) {
            model.addAttribute("message", "Неверно заполнены поля");
            return "error";
        }
        return "redirect:/all";
    }

    @GetMapping("/create")
    public String getAddPage(@ModelAttribute Task task) {
        return "create";
    }

    @PostMapping("/create")
    public String add(@ModelAttribute TaskDTO task, Model model) {
        try {
            service.add(task);
        } catch (NumberFormatException nfe) {
            model.addAttribute("message", "Неверно заполнены поля");
            return "error";
        }
        return "redirect:/all";
    }
}