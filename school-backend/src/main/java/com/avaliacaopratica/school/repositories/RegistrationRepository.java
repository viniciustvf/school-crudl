package com.avaliacaopratica.school.repositories;

import com.avaliacaopratica.school.models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, UUID> {
    List<Registration> findByPersonId(UUID personId);
    List<Registration> findByCourseId(UUID courseId);
    List<Registration> findByCourseIdAndPersonId(UUID courseId, UUID personId);
}
