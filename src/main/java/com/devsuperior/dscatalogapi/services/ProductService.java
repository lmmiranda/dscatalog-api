package com.devsuperior.dscatalogapi.services;

import com.devsuperior.dscatalogapi.dtos.CategoryDTO;
import com.devsuperior.dscatalogapi.dtos.ProductDTO;
import com.devsuperior.dscatalogapi.entities.Product;
import com.devsuperior.dscatalogapi.exceptionhandler.excpetions.ResourceNotFoundException;
import com.devsuperior.dscatalogapi.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(PageRequest pageRequest) {
        Page<Product> productsPage = productRepository.findAll(pageRequest);
        return productsPage.map(product -> convertFromEntity(product));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.orElseThrow(() -> new ResourceNotFoundException(""));
        return convertFromEntity(product);
    }

    private ProductDTO convertFromEntity(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}
