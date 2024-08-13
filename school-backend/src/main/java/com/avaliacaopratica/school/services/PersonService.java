package com.avaliacaopratica.school.services;

import com.avaliacaopratica.school.models.Person;
import com.avaliacaopratica.school.models.Person;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    Person findById(UUID id);
    Person insert(Person person);
    Person update(Person person);
    void delete(UUID id);
    List<Person> listAll();

}
