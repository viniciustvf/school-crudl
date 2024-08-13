package com.avaliacaopratica.school.services;

import com.avaliacaopratica.school.BaseTests;
import com.avaliacaopratica.school.models.Registration;
import com.avaliacaopratica.school.models.Course;
import com.avaliacaopratica.school.models.Person;
import com.avaliacaopratica.school.services.exceptions.IntegrityViolation;
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
public class RegistrationServiceImplTest extends BaseTests {

    @Autowired
    RegistrationService registrationService;

    @Test
    @DisplayName("Teste buscar matrícula por ID")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql", "classpath:/sqls/registration.sql"})
    void findByIdTest() {
        var registration = registrationService.findById(UUID.fromString("b5a44a48-02b0-4e22-9dcd-7c7bd66fa3ed"));
        assertNotNull(registration);
        assertEquals(UUID.fromString("b5a44a48-02b0-4e22-9dcd-7c7bd66fa3ed"), registration.getId());
    }

    @Test
    @DisplayName("Teste inserir matrícula")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql"})
    void insertRegistrationTest() {
        Person person = new Person(UUID.fromString("9ab1a3db-00fb-4864-b2d5-f1f362fb7bfd"), "John Doe", "13260581936", LocalDate.of(2000, 1, 1));
        Course course = new Course(UUID.fromString("bba5b01d-742b-41fc-a2d1-2b0a7276a495"), "Curso Teste", 10, LocalDate.now().plusDays(10), LocalDate.now().plusDays(20), 18);
        Registration registration = new Registration(null, person, LocalDate.now(), course);

        var savedRegistration = registrationService.insert(registration);
        var foundRegistration = registrationService.findById(savedRegistration.getId());

        assertNotNull(foundRegistration);
        assertEquals(person.getId(), foundRegistration.getPerson().getId());
        assertEquals(course.getId(), foundRegistration.getCourse().getId());
    }

    @Test
    @DisplayName("Teste inserir matrícula com idade insuficiente")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql"})
    void insertRegistrationUnderageTest() {
        Person person = new Person(UUID.fromString("9ab1a3db-00fb-4864-b2d5-f1f362fb7bfd"), "John Doe", "13260581936", LocalDate.of(2010, 1, 1));
        Course course = new Course(UUID.fromString("bba5b01d-742b-41fc-a2d1-2b0a7276a495"), "Curso Teste", 10, LocalDate.now().plusDays(10), LocalDate.now().plusDays(20), 18);
        Registration registration = new Registration(null, person, LocalDate.now(), course);

        assertThrows(IntegrityViolation.class, () -> {
            registrationService.insert(registration);
        });
    }

    @Test
    @DisplayName("Teste atualizar matrícula")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql", "classpath:/sqls/registration.sql"})
    void updateRegistrationTest() {
        var registration = registrationService.findById(UUID.fromString("b5a44a48-02b0-4e22-9dcd-7c7bd66fa3ed"));
        registration.setRegistrationDate(LocalDate.now().minusDays(1));

        var updatedRegistration = registrationService.update(registration);
        assertNotNull(updatedRegistration);
        assertEquals(LocalDate.now().minusDays(1), updatedRegistration.getRegistrationDate());
    }

    @Test
    @DisplayName("Teste deletar matrícula")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql", "classpath:/sqls/registration.sql"})
    void deleteRegistrationTest() {
        var registration = registrationService.findById(UUID.fromString("b5a44a48-02b0-4e22-9dcd-7c7bd66fa3ed"));
        assertNotNull(registration);
        registrationService.delete(registration.getId());

        assertThrows(ObjectNotFound.class, () -> {
            registrationService.findById(UUID.fromString("b5a44a48-02b0-4e22-9dcd-7c7bd66fa3ed"));
        });
    }

    @Test
    @DisplayName("Teste listar todas as matrículas")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql", "classpath:/sqls/registration.sql"})
    void listAllTest() {
        List<Registration> registrations = registrationService.listAll();
        assertNotNull(registrations);
        assertTrue(registrations.size() > 0);
    }

    @Test
    @DisplayName("Teste buscar matrículas por ID de pessoa")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql", "classpath:/sqls/registration.sql"})
    void getRegistrationsByPersonIdTest() {
        List<Registration> registrations = registrationService.getRegistrationsByPersonId(UUID.fromString("9ab1a3db-00fb-4864-b2d5-f1f362fb7bfd"));
        assertNotNull(registrations);
        assertTrue(registrations.size() > 0);
    }

    @Test
    @DisplayName("Teste buscar matrículas por ID de curso")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql", "classpath:/sqls/registration.sql"})
    void getRegistrationsByCourseIdTest() {
        List<Registration> registrations = registrationService.getRegistrationsByCourseId(UUID.fromString("bba5b01d-742b-41fc-a2d1-2b0a7276a495"));
        assertNotNull(registrations);
        assertTrue(registrations.size() > 0);
    }

    @Test
    @DisplayName("Teste inserir matrícula com data superior ao início do curso")
    @Sql({"classpath:/sqls/clean_tables.sql", "classpath:/sqls/person.sql", "classpath:/sqls/course.sql"})
    void insertRegistrationAfterCourseStartTest() {
        Person person = new Person(UUID.fromString("9ab1a3db-00fb-4864-b2d5-f1f362fb7bfd"), "John Doe", "13260581936", LocalDate.of(2000, 1, 1));
        Course course = new Course(UUID.fromString("bba5b01d-742b-41fc-a2d1-2b0a7276a495"), "Curso Teste", 10, LocalDate.now().minusDays(1), LocalDate.now().plusDays(20), 18);
        Registration registration = new Registration(null, person, LocalDate.now(), course);

        assertThrows(IntegrityViolation.class, () -> {
            registrationService.insert(registration);
        });
    }
}