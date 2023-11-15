package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleTaskRepository implements TaskRepository {
    private final CrudRepository crudRepository;

    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public boolean deleteById(int id) {
        crudRepository.run(
                "delete from Task where id = :fId",
                Map.of("fId", id)
        );
        return true;
    }

    @Override
    public boolean update(Task task) {
        crudRepository.run(session -> session.merge(task));
        return true;
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                "from Task f JOIN FETCH f.priority where f.id = :fId", Task.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Task> findAll() {
        return crudRepository.query("from Task f JOIN FETCH f.priority order by f.id asc", Task.class);
    }

    @Override
    public List<Task> findByCondition(boolean expression) {
        return crudRepository.query("from Task f JOIN FETCH f.priority where done = :fExpression order by f.id asc", Task.class,
                Map.of("fExpression", expression)
        );
    }

    @Override
    public boolean setDone(Task task) {
        crudRepository.run("update Task set done = :fDone where id = :fId",
                Map.of("fDone", true, "fId", task.getId())
        );
        return true;
    }
}
