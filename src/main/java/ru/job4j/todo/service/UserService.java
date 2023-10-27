package ru.job4j.todo.service;

import ru.job4j.todo.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> save(User user);

    boolean deleteById(int id);

    Optional<User> findByEmailAndPassword(String email, String password);

    List<User> findAll();
}
