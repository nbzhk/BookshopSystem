package com.example.bookshopsystem.services;

import com.example.bookshopsystem.entities.Category;
import com.example.bookshopsystem.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    @Override
    public Set<Category> getRandomCategories() {
        List<Category> allCategories = categoryRepository.findAll();
        Set<Category> randomCategories = new HashSet<>();

        Random random = new Random();
        int num = random.nextInt(allCategories.size());

        for (int i = 0; i < num; i++) {
            Category category = allCategories.get(i);
            randomCategories.add(category);
        }

        return randomCategories;
    }
}
