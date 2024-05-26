package ru.job4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.controller.utils.TimeZoneStorage;
import ru.job4j.model.User;
import ru.job4j.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final TimeZoneStorage timeZoneStorage;

    public UserController(UserService userService, TimeZoneStorage timeZoneStorage) {
        this.userService = userService;
        this.timeZoneStorage = timeZoneStorage;
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("defaultTimeZoneID", timeZoneStorage.getDefaultParams().getID());
        model.addAttribute("timeZones", timeZoneStorage.getTimeZoneElements());
        return "users/register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute User user, @RequestParam String timeZone) {
        user.setTimezone(timeZone);
        Optional<User> result = userService.save(user);
        if (result.isEmpty()) {
            model.addAttribute("error", "Не удалось сохранить пользователя.");
            return "/users/register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = userService.findByEmailAndPassword(user.getLogin(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Почта или пароль введены неверно");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/tasks";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
