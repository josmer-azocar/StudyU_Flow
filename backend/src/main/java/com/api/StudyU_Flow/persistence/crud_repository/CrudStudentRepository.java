package com.api.StudyU_Flow.persistence.crud_repository;

import com.api.StudyU_Flow.persistence.entity.StudentDegreeEntity;
import com.api.StudyU_Flow.persistence.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CrudStudentRepository extends CrudRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByUsername(String username);
    StudentEntity findFirstByUsername(String username);

}
