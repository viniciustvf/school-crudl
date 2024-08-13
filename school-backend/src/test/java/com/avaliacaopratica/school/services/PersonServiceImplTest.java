package com.avaliacaopratica.school.services;

import com.avaliacaopratica.school.BaseTests;
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
public class PersonServiceImplTest extends BaseTests {

    @Autowired
    PersonService personService;

    @Test
    @DisplayName("Teste buscar pessoa por ID")
    @Sql({"classpath:/sqls/person.sql"})
    void findByIdTest() {
        var person = personService.findById(UUID.fromString("9ab1a3db-00fb-4864-b2d5-f1f362fb7bfd"));
        assertNotNull(person);
        assertEquals(person.getId(), UUID.fromString("9ab1a3db-00fb-4864-b2d5-f1f362fb7bfd"));
    }

    @Test
    @DisplayName("Teste inserir pessoa")
    @Sql({"classpath:/sqls/clean_tables.sql"})
    void insertPessoaTest() {
        var person = personService.insert(new Person(null, "John Doe", "13260581936", LocalDate.now()));
        var person2 = personService.findById(person.getId());
        assertNotNull(person2);
        assertEquals("John Doe", person2.getName());
        assertEquals("13260581936", person2.getCpf());
    }

    @Test
    @DisplayName("Teste inserir pessoa com CPF inválido")
    @Sql({"classpath:/sqls/clean_tables.sql"})
    void insertPessoaInvalidCpfTest() {
        assertThrows(IntegrityViolation.class, () -> {
            personService.insert(new Person(null, "John Doe", "11111111111", LocalDate.now()));
        });
    }

    @Test
    @DisplayName("Teste atualizar pessoa")
    @Sql({"classpath:/sqls/clean_tables.sql"})
    void updatePessoaTest() {
        var person = personService.insert(new Person(null, "John Doe", "13260581936", LocalDate.now()));
        person.setName("Jane Doe");
        var updatedPerson = personService.update(person);
        assertNotNull(updatedPerson);
        assertEquals("Jane Doe", updatedPerson.getName());
    }

    @Test
    @DisplayName("Teste atualizar pessoa com CPF inválido")
    @Sql({"classpath:/sqls/clean_tables.sql"})
    void updatePessoaInvalidCpfTest() {
        var person = personService.insert(new Person(null, "John Doe", "13260581936", LocalDate.now()));
        person.setCpf("11111111111");
        assertThrows(IntegrityViolation.class, () -> {
            personService.update(person);
        });
    }

    @Test
    @DisplayName("Teste deletar pessoa")
    @Sql({"classpath:/sqls/person.sql"})
    void deletePessoaTest() {
        var person = personService.findById(UUID.fromString("9ab1a3db-00fb-4864-b2d5-f1f362fb7bfd"));
        assertNotNull(person);
        personService.delete(person.getId());
        assertThrows(ObjectNotFound.class, () -> {
            personService.findById(person.getId());
        });
    }

    @Test
    @DisplayName("Teste listar todas as pessoas")
    @Sql({"classpath:/sqls/person.sql"})
    void listAllTest() {
        List<Person> persons = personService.listAll();
        assertNotNull(persons);
        assertTrue(persons.size() > 0);
    }

    @Test
    @DisplayName("Teste buscar pessoa por ID inexistente")
    @Sql({"classpath:/sqls/clean_tables.sql"})
    void findByIdNonExistentTest() {
        assertThrows(ObjectNotFound.class, () -> {
            personService.findById(UUID.randomUUID());
        });
    }
}
