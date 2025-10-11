package com.api.StudyU_Flow.persistence.crud_repository;
import com.api.StudyU_Flow.persistence.entity.SubjectEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudSubjectRepository extends CrudRepository<SubjectEntity, Long> {
}
