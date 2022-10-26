package com.devsuperior.dscatalogapi.services;

import com.devsuperior.dscatalogapi.dtos.CategoryDTO;
import com.devsuperior.dscatalogapi.entities.Category;
import com.devsuperior.dscatalogapi.exceptionhandler.excpetions.BusinessException;
import com.devsuperior.dscatalogapi.exceptionhandler.excpetions.ResourceNotFoundException;
import com.devsuperior.dscatalogapi.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(PageRequest pageRequest) {
        Page<Category> categoriesPage = categoryRepository.findAll(pageRequest);
        return categoriesPage.map(category -> convertFromEntity(category));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Category category = optionalCategory.orElseThrow(() -> new ResourceNotFoundException(format("Category not found for id: %s", id)));
        return convertFromEntity(category);
    }

    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = convertFromDto(categoryDTO);
        category = categoryRepository.save(category);
        return convertFromEntity(category);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        try {
            Category category = categoryRepository.getReferenceById(id);
            category.setName(categoryDTO.getName());
            category = categoryRepository.save(category);
            return convertFromEntity(category);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(format("Category not found for id: %s", id));
        }
    }

    public void deleteById(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(format("Category not found for id: %s", id));
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Could not delete category because it has related products");
        }

    }

    private CategoryDTO convertFromEntity(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    private Category convertFromDto(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }
}
