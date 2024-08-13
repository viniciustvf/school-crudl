package com.avaliacaopratica.school.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private UUID id;
    private String name;
    private String cpf;
    private String birthDate;

}
