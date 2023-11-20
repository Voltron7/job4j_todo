package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class SimpleCategoryRepository implements CategoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Category> findByCategoriesIdsList(List<Integer> categoriesIdsList) {
        return crudRepository.query(
                "from Category where id IN :idsList", Category.class,
                Map.of("idsList", categoriesIdsList)
        );
    }

    @Override
    public List<Category> findAll() {
        return crudRepository.query("from Category order by id asc", Category.class);
    }
}
