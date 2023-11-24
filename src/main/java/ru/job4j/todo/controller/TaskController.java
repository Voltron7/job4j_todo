package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.util.TaskZone;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String getAll(Model model, @SessionAttribute User user) {
        List<Task> tasks = taskService.findAll();
        for (Task task : tasks) {
            task.setCreated(TaskZone.setUsersTimeZone(task, user));
        }
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, @SessionAttribute User user, @RequestParam Set<Integer> categoriesIds) {
        task.setUser(user);
        task.setCategories(categoryService.findByCategoriesIds(categoriesIds));
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
    public String update(@ModelAttribute Task task, Model model, @SessionAttribute User user, @RequestParam Set<Integer> categoriesIds) {
        task.setUser(user);
        task.setCategories(categoryService.findByCategoriesIds(categoriesIds));
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено!");
            return "errors/404";
        }
        return "redirect:/tasks/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, @SessionAttribute User user) {
        var taskOptional = taskService.findById(id);
        taskOptional.get().setCreated(TaskZone.setUsersTimeZone(taskOptional.get(), user));
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
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/edit";
    }

    @GetMapping("/done")
    public String getDonePage(Model model, @SessionAttribute User user) {
        List<Task> tasks = taskService.findByCondition(true);
        for (Task task : tasks) {
            task.setCreated(TaskZone.setUsersTimeZone(task, user));
        }
        model.addAttribute("tasks", tasks);
        return "tasks/done";
    }

    @GetMapping("/new")
    public String getNewPage(Model model, @SessionAttribute User user) {
        List<Task> tasks = taskService.findByCondition(false);
        for (Task task : tasks) {
            task.setCreated(TaskZone.setUsersTimeZone(task, user));
        }
        model.addAttribute("tasks", tasks);
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
