package com.avaliacaopratica.school.services.impl;

import com.avaliacaopratica.school.models.Registration;
import com.avaliacaopratica.school.repositories.RegistrationRepository;
import com.avaliacaopratica.school.services.RegistrationService;
import com.avaliacaopratica.school.services.exceptions.IntegrityViolation;
import com.avaliacaopratica.school.services.exceptions.ObjectNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.avaliacaopratica.school.utils.DateUtils.calculateAge;
import static com.avaliacaopratica.school.utils.Utilities.isNotEmpty;


@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationRepository repository;

    private void validate(Registration registration, boolean isInsert) {
        if (registration.getCourse().getTotalVacancies() <= repository.findByCourseId(registration.getCourse().getId()).size()) {
            throw new IntegrityViolation("Todas as vagas já foram preenchidas.");
        }

        if (calculateAge(registration.getPerson().getBirthDate()) < registration.getCourse().getMinimumStudentAge()) {
            throw new IntegrityViolation("Pessoa com idade menor do que o requisito mínimo do curso.");
        }

        if (registration.getRegistrationDate().isAfter(registration.getCourse().getInit())) {
            throw new IntegrityViolation("Data da matrícula superior ao inicio do curso.");
        }

        if (isNotEmpty(repository.findByCourseIdAndPersonId(registration.getCourse().getId(), registration.getPerson().getId())) && isInsert) {
            throw new IntegrityViolation("Aluno não pode se matricular no mesmo curso duas vezes.");
        }
    }

    @Override
    public Registration findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("A matrícula com ID %s não existe".formatted(id)));
    }

    @Override
    public Registration insert(Registration registration) {
        validate(registration, true);
        return repository.save(registration);
    }

    @Override
    public Registration update(Registration registration) {
        validate(registration, false);
        findById(registration.getId());
        return repository.save(registration);
    }

    @Override
    public void delete(UUID id) {
        Registration registration = findById(id);
        repository.delete(registration);
    }

    @Override
    public List<Registration> listAll() {
        return repository.findAll();
    }

    @Override
    public List<Registration> getRegistrationsByPersonId(UUID personId) {
        return repository.findByPersonId(personId);
    }

    @Override
    public List<Registration> getRegistrationsByCourseId(UUID courseId) {
        return repository.findByCourseId(courseId);
    }
}
