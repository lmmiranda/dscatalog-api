package com.devsuperior.dscatalogapi.services;

import com.devsuperior.dscatalogapi.convertes.CategoryConverter;
import com.devsuperior.dscatalogapi.dtos.CategoryDTO;
import com.devsuperior.dscatalogapi.entities.Category;
import com.devsuperior.dscatalogapi.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryConverter.createFromEntities(categories);
    }
}
