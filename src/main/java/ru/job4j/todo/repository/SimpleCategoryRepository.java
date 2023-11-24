package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
@AllArgsConstructor
public class SimpleCategoryRepository implements CategoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public Set<Category> findByCategoriesIds(Set<Integer> categoriesIds) {
        return new HashSet<>(crudRepository.query(
                "from Category where id IN :ids", Category.class,
                Map.of("ids", categoriesIds))
        );
    }

    @Override
    public List<Category> findAll() {
        return crudRepository.query("from Category order by id asc", Category.class);
    }
}
