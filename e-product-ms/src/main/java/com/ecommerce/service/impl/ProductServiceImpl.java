package com.ecommerce.service.impl;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> obj = this.productRepository.findById(id);
        return obj.orElse(null);
    }

    @Override
    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Product update(Product product, Long id) {
        product.setId(id);
        return this.productRepository.save(product);
    }

    @Override
    public Product delete(Long id) {
        Optional<Product> excludedProduct = this.productRepository.findById(id);
        this.productRepository.deleteById(id);
        return excludedProduct.orElse(null);
    }
}
