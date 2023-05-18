package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService service;

    public UserController(UserService simpleUserService) {
        service = simpleUserService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var savedUser = service.findByLogin(user.getLogin());
        if (savedUser.isEmpty()) {
            model.addAttribute("message", "Логин или пароль введены неверно");
            return "error";
        }
        request.getSession().setAttribute("user", savedUser.get());
        return "redirect:/task/all";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "/user/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        var isAdd = service.add(user);
        if (!isAdd) {
            model.addAttribute("message", "Логин уже используется");
            return "error";
        }
        return "/user/login";
    }
}
