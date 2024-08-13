package com.avaliacaopratica.school.services;

import com.avaliacaopratica.school.models.Course;
import com.avaliacaopratica.school.models.Course;
import com.avaliacaopratica.school.models.Registration;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    Course findById(UUID id);
    Course insert(Course course);
    Course update(Course course);
    void delete(UUID id);
    List<Course> listAll();
    
}
