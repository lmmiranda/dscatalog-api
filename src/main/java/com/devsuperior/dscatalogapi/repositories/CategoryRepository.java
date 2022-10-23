package com.devsuperior.dscatalogapi.repositories;

import com.devsuperior.dscatalogapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
