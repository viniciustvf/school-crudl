package com.avaliacaopratica.school.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    private UUID id;
    private String name;
    private Integer totalVacancies;
    private String init;
    private String end;
    private Integer minimumStudentAge;

}
