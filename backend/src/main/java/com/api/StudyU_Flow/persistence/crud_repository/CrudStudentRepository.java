package com.api.StudyU_Flow.persistence.crud_repository;

import com.api.StudyU_Flow.persistence.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface CrudStudentRepository extends CrudRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByUsername(String username);
    StudentEntity findFirstByUsername(String username);
    Void deleteByIdStudent(Long idStudent);
    StudentEntity findByIdStudent(Long idStudent);
}
