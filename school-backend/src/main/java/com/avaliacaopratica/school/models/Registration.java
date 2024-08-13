package com.avaliacaopratica.school.models;

import com.avaliacaopratica.school.models.dto.RegistrationDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

import static com.avaliacaopratica.school.utils.DateUtils.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registration")
public class Registration {

    @Id
    @Setter
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @NotNull(message = "A data da matrícula não pode estar em branco")
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Registration(RegistrationDTO dto, Person person, Course course) {
        this(dto.getId(), person, jsonToLocalDate(dto.getRegistrationDate()), course);
    }

    public RegistrationDTO toDTO() {
        return new RegistrationDTO(id, person, course, person.getId(), course.getId(), localDateToStr(registrationDate));
    }
}