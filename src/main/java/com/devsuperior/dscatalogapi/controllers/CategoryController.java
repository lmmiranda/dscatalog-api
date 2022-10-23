package com.devsuperior.dscatalogapi.controllers;

import com.devsuperior.dscatalogapi.dtos.CategoryDTO;
import com.devsuperior.dscatalogapi.entities.Category;
import com.devsuperior.dscatalogapi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> categories = categoryService.findAll();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("{id}")
    public CategoryDTO findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }
}
