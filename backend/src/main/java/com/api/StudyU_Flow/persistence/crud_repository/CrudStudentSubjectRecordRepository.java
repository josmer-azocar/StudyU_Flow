package com.api.StudyU_Flow.persistence.crud_repository;

import com.api.StudyU_Flow.persistence.entity.StudentSubjectRecordEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudStudentSubjectRecordRepository extends CrudRepository<StudentSubjectRecordEntity, Long> {

    StudentSubjectRecordEntity findBySubject_IdSubject(Long idSubject);

    StudentSubjectRecordEntity findFirstByIdRecord(Long idRecord);
}
