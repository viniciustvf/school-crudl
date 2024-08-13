package com.avaliacaopratica.school.resources;

import com.avaliacaopratica.school.models.Person;
import com.avaliacaopratica.school.models.dto.PersonDTO;
import com.avaliacaopratica.school.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/person")
public class PersonResource {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(personService.findById(id).toDTO());
    }

    @PostMapping
    public ResponseEntity<PersonDTO> insert(@RequestBody PersonDTO personDTO) {
        return ResponseEntity.ok(personService.insert(new Person(personDTO)).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable UUID id, @RequestBody PersonDTO personDTO) {
        Person person = new Person(personDTO);
        person.setId(id);
        return ResponseEntity.ok(personService.update(person).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> listAll () {
        return ResponseEntity.ok(personService.listAll().stream().map(Person::toDTO).toList());
    }
}
