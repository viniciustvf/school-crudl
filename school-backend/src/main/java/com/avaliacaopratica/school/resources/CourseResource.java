package com.avaliacaopratica.school.resources;

import com.avaliacaopratica.school.models.Course;
import com.avaliacaopratica.school.models.Registration;
import com.avaliacaopratica.school.models.dto.CourseDTO;
import com.avaliacaopratica.school.models.dto.RegistrationDTO;
import com.avaliacaopratica.school.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
public class CourseResource {

    @Autowired
    private CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(courseService.findById(id).toDTO());
    }

    @PostMapping
    public ResponseEntity<CourseDTO> insert(@RequestBody CourseDTO courseDTO) {
        Course course = new Course(courseDTO);
        course.setId(UUID.randomUUID());
        return ResponseEntity.ok(courseService.insert(new Course(courseDTO)).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable UUID id, @RequestBody CourseDTO courseDTO) {
        Course course = new Course(courseDTO);
        course.setId(id);
        return ResponseEntity.ok(courseService.update(course).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> listAll() {
        return ResponseEntity.ok(courseService.listAll().stream().map(Course::toDTO).toList());
    }
}
