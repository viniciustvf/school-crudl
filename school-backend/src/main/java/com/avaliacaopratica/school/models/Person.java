package com.avaliacaopratica.school.models;

import com.avaliacaopratica.school.models.dto.PersonDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "person")
public class Person {

    @Id
    @Setter
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    @NotBlank(message = "Nome não pode estar em branco")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "O CPF não pode estar em branco")
    @Column(name = "cpf", unique = true)
    private String cpf;

    @NotNull(message = "A data de nascimento não pode estar em branco")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    public Person(PersonDTO dto) {
        this(dto.getId(), dto.getName(), dto.getCpf(), jsonToLocalDate(dto.getBirthDate()));
    }

    public PersonDTO toDTO() {
        return new PersonDTO(id, name, cpf, localDateToStr(birthDate));
    }
}