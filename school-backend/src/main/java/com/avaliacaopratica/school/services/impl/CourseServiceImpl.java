package com.avaliacaopratica.school.services.impl;

import com.avaliacaopratica.school.models.Course;
import com.avaliacaopratica.school.repositories.CourseRepository;
import com.avaliacaopratica.school.services.CourseService;
import com.avaliacaopratica.school.services.exceptions.ObjectNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Override
    public Course findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("A matrícula com ID %s não existe".formatted(id)));
    }

    @Override
    public Course insert(Course course) {
        return repository.save(course);
    }

    @Override
    public Course update(Course course) {
        findById(course.getId());
        return repository.save(course);
    }

    @Override
    public void delete(UUID id) {
        Course course = findById(id);
        repository.delete(course);
    }

    @Override
    public List<Course> listAll() {
        return repository.findAll();
    }
}
