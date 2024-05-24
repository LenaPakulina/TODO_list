package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.model.Task;
import ru.job4j.model.User;
import ru.job4j.service.PriorityService;
import ru.job4j.service.TaskService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/tasks")
@SessionAttributes("user")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final PriorityService priorityService;

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("statusFlag", "GetAll");
        return "/tasks/show_tasks";
    }

    @GetMapping("/status/done")
    public String getDoneTasks(Model model) {
        model.addAttribute("tasks", taskService.findDoneTasks());
        model.addAttribute("statusFlag", "GetDone");
        return "/tasks/show_tasks";
    }

    @GetMapping("/status/todo")
    public String getNewTasks(Model model) {
        model.addAttribute("tasks", taskService.findNewTasks());
        model.addAttribute("statusFlag", "GetNew");
        return "/tasks/show_tasks";
    }

    @GetMapping("/add")
    public String getCreationPage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        return "/tasks/add";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute Task task, Model model, @SessionAttribute("user") User user) {
        task.setUser(user);
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Заявка с указанным идентификатором не найдена.");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "/tasks/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Заявка с указанным идентификатором не найдена.");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Заявка с указанным идентификатором не найдена.");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/switch/{id}/{done}")
    public String switchStatus(Model model, @PathVariable int id, @PathVariable boolean done) {
        var isSuccess = taskService.switchStatusDone(id, done);
        if (!isSuccess) {
            model.addAttribute("message", "Заявка с указанным идентификатором не найдена.");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        var currTask = taskService.findById(id);
        if (currTask.isEmpty()) {
            model.addAttribute("message", "Заявка с указанным идентификатором не найдена.");
            return "errors/404";
        }
        model.addAttribute("task", currTask.get());
        model.addAttribute("priorities", priorityService.findAll());
        return "/tasks/edit";
    }
}
