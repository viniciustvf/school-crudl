package com.avaliacaopratica.school.services;

import com.avaliacaopratica.school.models.Registration;
import com.avaliacaopratica.school.models.dto.RegistrationDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface RegistrationService {

    Registration findById(UUID id);
    Registration insert(Registration registration);
    Registration update(Registration registration);
    void delete(UUID id);
    List<Registration> listAll();
    List<Registration> getRegistrationsByPersonId(UUID personId);
    List<Registration> getRegistrationsByCourseId(UUID courseId);
    
}
