package com.avaliacaopratica.school.models;

import com.avaliacaopratica.school.models.dto.CourseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
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
@Table(name = "course")
public class Course {

    @Id
    @Setter
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Positive
    @Column(name = "total_vacancies")
    private Integer totalVacancies;

    @Column(name = "init_date")
    private LocalDate init;

    @Column(name = "end_date")
    private LocalDate end;

    @Positive
    @Column(name = "minimum_student_age")
    private Integer minimumStudentAge;

    public Course(CourseDTO dto) {
        this(dto.getId(),
                dto.getName(), dto.getTotalVacancies(),
                jsonToLocalDate(dto.getInit()),
                jsonToLocalDate(dto.getEnd()),
                dto.getMinimumStudentAge());
    }

    public CourseDTO toDTO() {
        return new CourseDTO(id, name, totalVacancies, localDateToStr(init), localDateToStr(end), minimumStudentAge);
    }
}
