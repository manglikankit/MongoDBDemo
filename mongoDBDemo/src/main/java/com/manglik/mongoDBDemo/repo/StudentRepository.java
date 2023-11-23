package com.manglik.mongoDBDemo.repo;

import com.manglik.mongoDBDemo.models.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, Integer> {

    @Query(value = "{'name':{$regex : ?0, $options: 'i'}}", fields = "{'college':0}") // To exclude college details in response
    Student getStudentByName(String name);
}
