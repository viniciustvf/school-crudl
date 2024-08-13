package com.avaliacaopratica.school.models.dto;

import com.avaliacaopratica.school.models.Course;
import com.avaliacaopratica.school.models.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {

    private UUID id;
    private UUID personId;
    private String personName;
    private Person person;
    private UUID courseId;
    private String courseName;
    private Course course;
    private String registrationDate;

    public RegistrationDTO(UUID id, Person person, Course course, UUID personId, UUID courseId, String registrationDate) {
        this.id = id;
        this.person = person;
        this.course = course;
        this.personId = personId;
        this.courseId = courseId;
        this.registrationDate = registrationDate;
    }
}
