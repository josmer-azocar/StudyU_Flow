package com.api.StudyU_Flow.persistence.crud_repository;

import com.api.StudyU_Flow.persistence.entity.EvaluationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CrudEvaluationRepository extends CrudRepository<EvaluationEntity, Long> {

    EvaluationEntity findFirstByIdEvaluation(Long idEvaluation);

    Void deleteByIdEvaluation(Long idEvaluation);

    List<EvaluationEntity> getAllByRecord_IdRecord(Long recordIdRecord);
}
