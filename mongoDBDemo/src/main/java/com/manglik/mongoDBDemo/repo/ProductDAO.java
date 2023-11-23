package com.manglik.mongoDBDemo.repo;

import com.manglik.mongoDBDemo.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Product> findAll(){
        return mongoTemplate.findAll(Product.class);
    }
    public void saveAll(final List<Product> products){
        mongoTemplate.insertAll(products);
    }
    public Product findById(final String productId){
        return mongoTemplate.findById(productId, Product.class);
    }

}
