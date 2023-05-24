package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/task")
public class TaskController {
    private TaskService taskService;
    private PriorityService priorityService;
    private CategoryService categoryService;

    public TaskController(TaskService simpleTaskService, PriorityService simplePriorityService,
                          CategoryService simpleCategoryService) {
        taskService = simpleTaskService;
        priorityService = simplePriorityService;
        categoryService = simpleCategoryService;
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "/task/list";
    }

    @GetMapping("/new")
    public String getAllNew(Model model) {
        model.addAttribute("tasks", taskService.findAllNew());
        return "/task/list";
    }

    @GetMapping("/done")
    public String getAllDone(Model model) {
        model.addAttribute("tasks", taskService.findAllDone());
        return "/task/list";
    }

    @GetMapping("/{id}")
    public String getTaskById(@PathVariable int id, Model model) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Неправильно указан номер задачи");
            return "error";
        }
        model.addAttribute("task", task.get());
        return "/task/task";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable int id) {
        taskService.deleteById(id);
        return "redirect:/task/all";
    }

    @GetMapping("/modify/{id}")
    public String getModifyPage(@PathVariable int id, Model model) {
        Collection<Priority> priorities = priorityService.findAll();
        Collection<Category> categories = categoryService.findAll();
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Неправильно указан номер задачи");
            return "error";
        }
        model.addAttribute("task", task.get())
                .addAttribute("priorities", priorities)
                .addAttribute("categories", categories);
        return "/task/modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Task task, HttpServletRequest request) {
        task.setUser((User) request.getSession().getAttribute("user"));
        taskService.update(task);
        return "redirect:/task/all";
    }

    @GetMapping("/create")
    public String getAddPage(@ModelAttribute Task task, Model model) {
        Collection<Priority> priorities = priorityService.findAll();
        Collection<Category> categories = categoryService.findAll();
        model.addAttribute("priorities", priorities)
                .addAttribute("categories", categories);
        return "/task/create";
    }

    @PostMapping("/create")
    public String add(@ModelAttribute Task task, HttpServletRequest request) {
        task.setUser((User) request.getSession().getAttribute("user"));
        task.setDone(false);
        task.setCreated(LocalDateTime.now());
        taskService.add(task);
        return "redirect:/task/all";
    }

    @PostMapping("/done/{id}")
    public String doneTask(@PathVariable String id, @ModelAttribute Task task) {
        taskService.doneById(Integer.parseInt(id), !task.isDone());
        return "redirect:/task/" + id;
    }
}