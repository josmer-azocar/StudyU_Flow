package com.api.StudyU_Flow.persistence.repository;

import com.api.StudyU_Flow.persistence.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByUsername(String username);
}
