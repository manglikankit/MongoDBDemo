package com.manglik.mongoDBDemo.repo;

import com.manglik.mongoDBDemo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import javax.swing.text.Document;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String>, CustomProductRepository {

    @Query(value = "{'price':{$gt:?0, $lt:?1}}", fields = "{image:0, url:0, id:0, }")
    List<Product> findProductByPriceAndBetween(Integer minPrice, Integer maxPrice);

}
