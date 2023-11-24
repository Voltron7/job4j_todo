package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Set<Category> findByCategoriesIds(Set<Integer> categoriesIds) {
        return categoryRepository.findByCategoriesIds(categoriesIds);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
