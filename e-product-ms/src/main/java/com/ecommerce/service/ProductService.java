package com.ecommerce.service;

import com.ecommerce.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Long id);

    Product save(Product product);

    Product update(Product product, Long id);

    Product delete(Long id);
}
