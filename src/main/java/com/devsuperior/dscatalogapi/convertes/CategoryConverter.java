package com.devsuperior.dscatalogapi.convertes;

import com.devsuperior.dscatalogapi.dtos.CategoryDTO;
import com.devsuperior.dscatalogapi.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter extends Converter<CategoryDTO, Category>{

    public CategoryConverter() {
        super(CategoryConverter::convertToEntity, CategoryConverter::convertToDto);
    }

    private static CategoryDTO convertToDto(Category category) {
        return new CategoryDTO(category.getId(),category.getName());
    }

    private static Category convertToEntity(CategoryDTO dto) {
        return new Category(dto.getId(), dto.getName());
    }
}
