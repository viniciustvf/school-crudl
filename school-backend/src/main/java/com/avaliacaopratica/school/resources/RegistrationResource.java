package com.avaliacaopratica.school.resources;

import com.avaliacaopratica.school.models.Course;
import com.avaliacaopratica.school.models.Person;
import com.avaliacaopratica.school.models.Registration;
import com.avaliacaopratica.school.models.dto.RegistrationDTO;
import com.avaliacaopratica.school.services.CourseService;
import com.avaliacaopratica.school.services.PersonService;
import com.avaliacaopratica.school.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/registration")
public class RegistrationResource {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(registrationService.findById(id).toDTO());
    }

    @PostMapping
    public ResponseEntity<RegistrationDTO> insert(@RequestBody RegistrationDTO registrationDTO) {
        Registration registration = new Registration(registrationDTO, personService.findById(registrationDTO.getPersonId()), courseService.findById(registrationDTO.getCourseId()));
        return ResponseEntity.ok(registrationService.insert(registration).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistrationDTO> update(@PathVariable UUID id, @RequestBody RegistrationDTO registrationDTO) {
        Registration registration = new Registration(registrationDTO, personService.findById(registrationDTO.getPersonId()), courseService.findById(registrationDTO.getCourseId()));
        registration.setId(id);
        return ResponseEntity.ok(registrationService.update(registrationService.update(registration)).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        registrationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RegistrationDTO>> listAll() {
        return ResponseEntity.ok(registrationService.listAll().stream().map(Registration::toDTO).toList());
    }

    @GetMapping("/find-by-person-id/{id}")
    public ResponseEntity<List<RegistrationDTO>> getRegistrationsByPersonId(@PathVariable UUID id) {
        return ResponseEntity.ok(registrationService.getRegistrationsByPersonId(id).stream().map(Registration::toDTO).toList());
    }

    @GetMapping("/find-by-course-id/{id}")
    public ResponseEntity<List<RegistrationDTO>> getRegistrationsByCourseId(@PathVariable UUID id) {
        return ResponseEntity.ok(registrationService.getRegistrationsByCourseId(id).stream().map(Registration::toDTO).toList());
    }
}
