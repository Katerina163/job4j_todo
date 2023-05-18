package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping({"/", "/all"})
    public String getIndex() {
        return "redirect:/task/all";
    }
}
