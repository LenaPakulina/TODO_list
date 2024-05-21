package ru.job4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.model.Task;
import ru.job4j.service.TaskService;

@Controller
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping({"/", "/tasks"})
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("statusFlag", "GetAll");
        return "task/show_tasks";
    }

    @GetMapping("tasks/status/done")
    public String getDoneTasks(Model model) {
        model.addAttribute("tasks", taskService.findDoneTasks());
        model.addAttribute("statusFlag", "GetDone");
        return "task/show_tasks";
    }

    @GetMapping("tasks/status/todo")
    public String getNewTasks(Model model) {
        model.addAttribute("tasks", taskService.findNewTasks());
        model.addAttribute("statusFlag", "GetNew");
        return "task/show_tasks";
    }

    @GetMapping("task/add")
    public String getCreationPage(Model model) {
        return "task/add";
    }

    @PostMapping("task/add")
    public String create(@ModelAttribute Task task, Model model) {
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/task/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Заявка с указанным идентификатором не найдена.");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "task/one";
    }

    @PostMapping("/task/update")
    public String update(@ModelAttribute Task task, Model model) {
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Заявка с указанным идентификатором не найдена.");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/task/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Заявка с указанным идентификатором не найдена.");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("task/switch/{id}/{done}")
    public String switchStatus(Model model, @PathVariable int id, @PathVariable boolean done) {
        var isSuccess = taskService.switchStatusDone(id, done);
        if (!isSuccess) {
            model.addAttribute("message", "Заявка с указанным идентификатором не найдена.");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/task/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        var currTask = taskService.findById(id);
        if (currTask.isEmpty()) {
            model.addAttribute("message", "Заявка с указанным идентификатором не найдена.");
            return "errors/404";
        }
        model.addAttribute("task", currTask.get());
        return "task/edit";
    }

    @PostMapping("/task/edit")
    public String edit(@ModelAttribute Task task, Model model) {
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Заявка с указанным идентификатором не найдена.");
            return "errors/404";
        }
        return "redirect:/tasks";
    }
}
