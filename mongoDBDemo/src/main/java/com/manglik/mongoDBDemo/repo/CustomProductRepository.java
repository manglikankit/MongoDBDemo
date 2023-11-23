package com.manglik.mongoDBDemo.repo;

import com.manglik.mongoDBDemo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.text.Document;

import java.util.List;

public interface CustomProductRepository {
    void partialUpdate(final String productId, final String fieldName, final Object objectValue);
    Page<Product> search(String name, Integer minAge, Integer maxAge, String brand, Pageable pageable);

    List<Product> getProductNameOrderByBrand(String name);
}
