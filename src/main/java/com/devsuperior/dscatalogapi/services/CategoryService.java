package com.devsuperior.dscatalogapi.services;

import com.devsuperior.dscatalogapi.convertes.CategoryConverter;
import com.devsuperior.dscatalogapi.dtos.CategoryDTO;
import com.devsuperior.dscatalogapi.entities.Category;
import com.devsuperior.dscatalogapi.exceptionhandler.excpetions.ResourceNotFoundException;
import com.devsuperior.dscatalogapi.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

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

    public CategoryDTO findById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category = optionalCategory.orElseThrow(() -> new ResourceNotFoundException(format("Category not found for id: %s", id)));
        return categoryConverter.convertFromEntity(category);
    }
}
