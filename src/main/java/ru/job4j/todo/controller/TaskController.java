package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final PriorityService priorityService;

    @GetMapping("/list")
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, HttpSession httpSession, @RequestParam int priorityId) {
        User user = (User) httpSession.getAttribute("user");
        task.setUser(user);
        Optional<Priority> priority = priorityService.findById(priorityId);
        task.setPriority(priority.get());
        taskService.save(task);
        return "redirect:/tasks/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено!");
            return "errors/404";
        }
        return "redirect:/tasks/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model, HttpSession httpSession, @RequestParam int priorityId) {
        User user = (User) httpSession.getAttribute("user");
        task.setUser(user);
        Optional<Priority> priority = priorityService.findById(priorityId);
        task.setPriority(priority.get());
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено!");
            return "errors/404";
        }
        return "redirect:/tasks/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено!");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping("/edit/{id}")
    public String getEditPageById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено!");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        model.addAttribute("priorities", priorityService.findAll());
        return "tasks/edit";
    }

    @GetMapping("/done")
    public String getDonePage(Model model) {
        model.addAttribute("tasks", taskService.findByCondition(true));
        return "tasks/done";
    }

    @GetMapping("/new")
    public String getNewPage(Model model) {
        model.addAttribute("tasks", taskService.findByCondition(false));
        return "tasks/new";
    }

    @PostMapping("/complete")
    public String complete(@ModelAttribute Task task, Model model) {
        var isUpdated = taskService.setDone(task);
        if (!isUpdated) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено!");
            return "errors/404";
        }
        return "redirect:/tasks/list";
    }
}
