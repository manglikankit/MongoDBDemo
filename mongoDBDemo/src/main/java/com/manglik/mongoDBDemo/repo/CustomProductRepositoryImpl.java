package com.manglik.mongoDBDemo.repo;

import com.manglik.mongoDBDemo.models.PriceCount;
import com.manglik.mongoDBDemo.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomProductRepositoryImpl implements CustomProductRepository{
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void partialUpdate(final String productId, String fieldName, Object objectValue) {
mongoTemplate.findAndModify(BasicQuery.query(Criteria.where("id").is(productId)),
        BasicUpdate.update(fieldName,objectValue),
        FindAndModifyOptions.none(), Product.class);
    }

    public Page<Product> search(String name, Integer minAge, Integer maxAge, String brand, Pageable pageable){

        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();
        if(name != null && !name.isEmpty()){
            criteria.add(Criteria.where("name").regex(name, "i"));
        }
        if(minAge != null && maxAge != null){
            criteria.add(Criteria.where("price").gte(minAge).lte(maxAge));
        }
        if(brand != null && !brand.isEmpty()){
            criteria.add(Criteria.where("brand").is(brand));
        }
        if(!criteria.isEmpty()){
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }
        Page<Product> page = PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Product.class
                ), pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0),Product.class));
        return page;
    }
    public List<Product> getProductNameOrderByBrand(String name){
        // Group
        GroupOperation groupByBrand = Aggregation.group("price").count().as("count");
        //MatchOperation
        //Sort Operation
        //Aggregation
        MatchOperation matchOperation = Aggregation.match(new Criteria("price").is(name));
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.DESC, "price"));
        Aggregation aggregation = Aggregation.newAggregation(matchOperation,sort);
        AggregationResults aggregationResults = mongoTemplate.aggregate(aggregation,"product", Product.class);
        return aggregationResults.getMappedResults();
    }
}
