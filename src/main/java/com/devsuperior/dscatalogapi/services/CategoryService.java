package com.devsuperior.dscatalogapi.services;

import com.devsuperior.dscatalogapi.entities.Category;
import com.devsuperior.dscatalogapi.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
