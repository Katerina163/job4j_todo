package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/task")
public class TaskController {
    private TaskService service;

    public TaskController(TaskService simpleTaskService) {
        service = simpleTaskService;
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "/task/list";
    }

    @GetMapping("/new")
    public String getAllNew(Model model) {
        model.addAttribute("tasks", service.findAllNew());
        return "/task/list";
    }

    @GetMapping("/done")
    public String getAllDone(Model model) {
        model.addAttribute("tasks", service.findAllDone());
        return "/task/list";
    }

    @GetMapping("/{id}")
    public String getTaskById(@PathVariable int id, HttpServletResponse response, Model model) {
        Optional<Task> task = service.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Неправильно указан номер задачи");
            return "error";
        }
        response.addCookie(new Cookie("done", Boolean.toString(task.get().isDone())));
        model.addAttribute("task", task.get());
        return "/task/task";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable int id, Model model) {
        var isDeleted = service.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Не удалось удалить");
            return "error";
        }
        return "redirect:/task/all";
    }

    @GetMapping("/modify/{id}")
    public String getModifyPage(@PathVariable int id, HttpServletResponse response, Model model) {
        response.addCookie(new Cookie("id", Integer.toString(id)));
        Optional<Task> task = service.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Неправильно указан номер задачи");
            return "error";
        }
        model.addAttribute("task", task.get());
        return "/task/modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Task task, @CookieValue(value = "id") String id, Model model) {
        task.setId(Integer.parseInt(id));
        var isUpdated = service.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Не удалось обновить");
            return "error";
        }
        return "redirect:/task/all";
    }

    @GetMapping("/create")
    public String getAddPage(@ModelAttribute Task task) {
        return "/task/create";
    }

    @PostMapping("/create")
    public String add(@ModelAttribute Task task) {
        service.add(task);
        return "redirect:/task/all";
    }

    @GetMapping("/done/{id}")
    public String getDo(@PathVariable String id, @CookieValue(value = "done") String done) {
        service.doneById(Integer.parseInt(id), !Boolean.parseBoolean(done));
        return "redirect:/task/" + id;
    }
}