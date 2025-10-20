package com.api.StudyU_Flow.persistence.crud_repository;

import com.api.StudyU_Flow.persistence.entity.StudentDegreeEntity;
import com.api.StudyU_Flow.persistence.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CrudStudentDegreeRepository extends CrudRepository<StudentDegreeEntity, Long> {

    StudentDegreeEntity findByIdStudentDegree(Long id);
    List<StudentDegreeEntity> findAllByStudent_Username(String username);
    StudentDegreeEntity findByIdStudentDegreeAndStudent_Username(Long idStudentDegree, String username);

    Integer countByStudent_Username(String studentUsername);

    Void deleteByIdStudentDegree(Long idStudentDegree);
}
