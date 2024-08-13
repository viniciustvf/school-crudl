package com.avaliacaopratica.school.services;

import com.avaliacaopratica.school.BaseTests;
import com.avaliacaopratica.school.models.Course;
import com.avaliacaopratica.school.services.exceptions.ObjectNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class CourseServiceImplTest extends BaseTests {

    @Autowired
    private CourseService courseService;

    @Test
    @DisplayName("Teste buscar curso por ID")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql", "classpath:/sqls/registration.sql"})
    void findByIdTest() {
        var course = courseService.findById(UUID.fromString("bba5b01d-742b-41fc-a2d1-2b0a7276a495"));
        assertNotNull(course);
        assertEquals(UUID.fromString("bba5b01d-742b-41fc-a2d1-2b0a7276a495"), course.getId());
    }

    @Test
    @DisplayName("Teste buscar curso por ID não existente")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql", "classpath:/sqls/registration.sql"})
    void findByIdNotFoundTest() {
        assertThrows(ObjectNotFound.class, () -> {
            courseService.findById(UUID.randomUUID());
        });
    }

    @Test
    @DisplayName("Teste inserir curso")
    @Sql({"classpath:/sqls/clean_tables.sql"})
    void insertCourseTest() {
        UUID newCourseId = UUID.randomUUID();
        Course course = new Course(newCourseId, "Curso Novo", 10,  LocalDate.now().plusDays(10), LocalDate.now().plusDays(20), 18);
        var savedCourse = courseService.insert(course);

        assertNotNull(savedCourse);
        assertEquals(newCourseId, course.getId());
        assertEquals("Curso Novo", savedCourse.getName());
        assertEquals(10, savedCourse.getTotalVacancies());
        assertEquals(18, savedCourse.getMinimumStudentAge());
        assertEquals(LocalDate.now().plusDays(10), savedCourse.getInit());
        assertEquals(LocalDate.now().plusDays(20), savedCourse.getEnd());
    }

    @Test
    @DisplayName("Teste atualizar curso")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql", "classpath:/sqls/registration.sql"})
    void updateCourseTest() {
        var course = courseService.findById(UUID.fromString("bba5b01d-742b-41fc-a2d1-2b0a7276a495"));
        course.setName("Curso Atualizado");

        var updatedCourse = courseService.update(course);
        assertNotNull(updatedCourse);
        assertEquals("Curso Atualizado", updatedCourse.getName());
    }

    @Test
    @DisplayName("Teste atualizar curso não existente")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql", "classpath:/sqls/registration.sql"})
    void updateCourseNotFoundTest() {
        Course course = new Course(UUID.randomUUID(), "Curso Novo", 10, LocalDate.now().plusDays(10), LocalDate.now().plusDays(20), 18);

        assertThrows(ObjectNotFound.class, () -> {
            courseService.update(course);
        });
    }

    @Test
    @DisplayName("Teste deletar curso")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql", "classpath:/sqls/registration.sql"})
    void deleteCourseTest() {
        var course = courseService.findById(UUID.fromString("bba5b01d-742b-41fc-a2d1-2b0a7276a495"));
        assertNotNull(course);

        courseService.delete(course.getId());

        assertThrows(ObjectNotFound.class, () -> {
            courseService.findById(UUID.fromString("bba5b01d-742b-41fc-a2d1-2b0a7276a495"));
        });
    }

    @Test
    @DisplayName("Teste deletar curso não existente")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql", "classpath:/sqls/registration.sql"})
    void deleteCourseNotFoundTest() {
        assertThrows(ObjectNotFound.class, () -> {
            courseService.delete(UUID.randomUUID());
        });
    }

    @Test
    @DisplayName("Teste listar todos os cursos")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql", "classpath:/sqls/registration.sql"})
    void listAllTest() {
        List<Course> courses = courseService.listAll();
        assertNotNull(courses);
        assertTrue(courses.size() > 0);
    }
}