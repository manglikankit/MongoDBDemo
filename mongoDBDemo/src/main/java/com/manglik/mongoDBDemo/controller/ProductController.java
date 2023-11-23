package com.manglik.mongoDBDemo.controller;

import com.manglik.mongoDBDemo.models.Product;
import com.manglik.mongoDBDemo.repo.ProductDAO;
import com.manglik.mongoDBDemo.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.Document;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Objects;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductDAO productDAO;

    // data save with Mongorepo and mongotemplate
    @PostMapping("/products")
    public void addProducts(@RequestBody final List<Product> products){
//        productRepository.saveAll(products);
        productDAO.saveAll(products);
    }

    // data fetching with Mongorepo and mongotemplate
    @GetMapping("/products")
    public List<Product> findProducts(){
//       return productRepository.findAll();
        return productDAO.findAll();
    }
    // fetch product buy iD with Mongorepo and mongotemplate
    @GetMapping("/product/{productId}")
    public Product findProductById(@PathVariable String productId){
//        return productRepository.findById(productId).orElseGet(Product::new);
        return productDAO.findById(productId);
    }

    // fetch data within range with Mongorepo and mongotemplate
    @GetMapping("/age")
    public List<Product> findProductByPriceAndBetween(@RequestParam(value = "minPrice") Integer minPrice,@RequestParam(value = "maxPrice") Integer maxPrice){
        return productRepository.findProductByPriceAndBetween(minPrice, maxPrice);
    }

    // fetch with generics with Mongorepo and mongotemplate
    @PatchMapping("/product/{productId}")
    public void partialUpdate(@PathVariable String productId, @RequestBody final Product product) throws Exception {
        for(Field field: Product.class.getDeclaredFields()){
            String fieldName = field.getName();
            if(fieldName.equals("id")){
                continue;
            }
            final Method getter = Product.class.getDeclaredMethod("get" + StringUtils.capitalize(fieldName));
            final Object fieldValue = getter.invoke(product);
            if(Objects.nonNull(fieldValue)){
                productRepository.partialUpdate(productId, fieldName, fieldValue);
            }
        }
    }

    // fetch with generics with Mongorepo and mongotemplate
    @GetMapping("/search")
    public Page<Product> searchProduct(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) Integer minAge,
                                       @RequestParam(required = false) Integer maxAge,
                                       @RequestParam(required = false) String brand,
                                       @RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "3") Integer size
    ){
        Pageable pageable = PageRequest.of(page,size);
        return productRepository.search(name,minAge,maxAge,brand,pageable);
    }

    @GetMapping("/productByBrand/{name}")
    public List<Product> getProductOrderByBrand(@PathVariable(value = "name") String name){
        return productRepository.getProductNameOrderByBrand(name);
    }

}
