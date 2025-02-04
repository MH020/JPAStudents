package org.example.jpastudents.controller;

import jakarta.servlet.http.HttpSession;
import org.example.jpastudents.model.Student;
import org.example.jpastudents.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private final StudentRepository studentRepository;
    private final HttpSession httpSession;

    public StudentRestController(StudentRepository studentRepository, HttpSession httpSession) {
        this.studentRepository = studentRepository;
        this.httpSession = httpSession;
    }


    @GetMapping ("/students")
    public ResponseEntity<List<Student>> listStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }
    @GetMapping("students/{name}")
    public List<Student> getallstudentsbyname(@PathVariable String name) {
        return studentRepository.findAllByName(name);
    }
    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    public Student postStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
    @PutMapping("/student")
    public ResponseEntity<Student> putStudent(@RequestBody Student student) {
        Optional<Student> orgStudent = studentRepository.findById(student.getId());
        if (orgStudent.isPresent()) {
            return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
        } else {
            Student notStudent = new Student();
            notStudent.setName("notfound");
            return new ResponseEntity<>(notStudent, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/student/{id}")
    public ResponseEntity<String> deletestudent(@PathVariable int id) {
        Optional<Student> orgStudent = studentRepository.findById(id);
        if (orgStudent.isPresent()) {
            studentRepository.deleteById(id);
            return ResponseEntity.ok("you are Terminated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("student not found yo");
        }

    }
}

