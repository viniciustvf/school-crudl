package com.avaliacaopratica.school.services.impl;

import com.avaliacaopratica.school.models.Person;
import com.avaliacaopratica.school.repositories.PersonRepository;
import com.avaliacaopratica.school.services.PersonService;
import com.avaliacaopratica.school.services.exceptions.IntegrityViolation;
import com.avaliacaopratica.school.services.exceptions.ObjectNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.avaliacaopratica.school.utils.Utilities.isCPF;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository repository;

    private void validate(Person person) {
        if (!isCPF(person.getCpf())) {
            throw new IntegrityViolation("CPF Inválido.");
        }
    }

    @Override
    public Person findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("A pessoa com ID %s não existe".formatted(id)));
    }

    @Override
    public Person insert(Person person) {
        validate(person);
        return repository.save(person);
    }

    @Override
    public Person update(Person person) {
        validate(person);
        findById(person.getId());
        return repository.save(person);
    }

    @Override
    public void delete(UUID id) {
        Person person = findById(id);
        repository.delete(person);
    }

    @Override
    public List<Person> listAll() {
        return repository.findAll();
    }
}
