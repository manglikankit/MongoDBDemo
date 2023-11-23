package com.manglik.mongoDBDemo.controller;

import com.manglik.mongoDBDemo.models.Student;
import com.manglik.mongoDBDemo.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student req){
        Student save = studentRepository.save(req);
        return ResponseEntity.ok(save);
    }
    @GetMapping
    public ResponseEntity<?> getStudents(){
        return ResponseEntity.ok(studentRepository.findAll());

    }
    @GetMapping("/{name}")
    public ResponseEntity<?> getStudentBYName(@PathVariable String name){
        return ResponseEntity.ok(studentRepository.getStudentByName(name));

    }

}
