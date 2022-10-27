package com.devsuperior.dscatalogapi.services;

import com.devsuperior.dscatalogapi.dtos.CategoryDTO;
import com.devsuperior.dscatalogapi.dtos.ProductDTO;
import com.devsuperior.dscatalogapi.entities.Category;
import com.devsuperior.dscatalogapi.entities.Product;
import com.devsuperior.dscatalogapi.exceptionhandler.excpetions.ResourceNotFoundException;
import com.devsuperior.dscatalogapi.repositories.CategoryRepository;
import com.devsuperior.dscatalogapi.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAll(pageable);
        return productsPage.map(this::convertFromEntity);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.orElseThrow(() -> new ResourceNotFoundException(format("Product not found for id: %s", id)));
        return convertFromEntity(product);
    }

    @Transactional
    public ProductDTO save(ProductDTO productDTO) {
        Product product = convertFromDTO(productDTO);
        product = productRepository.save(product);
        return convertFromEntity(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        try {
            Product product = productRepository.getReferenceById(id);
            copyProperties(productDTO, product);
            product = productRepository.save(product);
            return convertFromEntity(product);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(format("Product not found for id: %s", id));
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(format("Product not found for id: %s", id));
        }
    }

    private void copyProperties(ProductDTO productDTO, Product product) {
        BeanUtils.copyProperties(productDTO, product, "id", "categories");

        product.getCategories().clear();
        for (CategoryDTO categoryDTO :
                productDTO.getCategories()) {
            Category category = categoryRepository.getReferenceById(categoryDTO.getId());
            product.getCategories().add(category);
        }
    }
    private Product convertFromDTO(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    private ProductDTO convertFromEntity(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

}
