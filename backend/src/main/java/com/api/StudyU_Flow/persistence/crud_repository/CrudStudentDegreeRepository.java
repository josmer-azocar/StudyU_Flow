package com.api.StudyU_Flow.persistence.crud_repository;

import com.api.StudyU_Flow.persistence.entity.StudentDegreeEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudStudentDegreeRepository extends CrudRepository<StudentDegreeEntity, Long> {
}
