package com.api.StudyU_Flow.persistence.impl_repository;

import com.api.StudyU_Flow.domain.dto.request.EvaluationRequestDto;
import com.api.StudyU_Flow.domain.dto.response.EvaluationResponseDto;
import com.api.StudyU_Flow.domain.exception.EvaluationDoesNotExistsException;
import com.api.StudyU_Flow.domain.exception.SubjectRecordDoesNotExistsException;
import com.api.StudyU_Flow.persistence.crud_repository.CrudEvaluationRepository;
import com.api.StudyU_Flow.persistence.crud_repository.CrudStudentSubjectRecordRepository;
import com.api.StudyU_Flow.persistence.entity.EvaluationEntity;
import com.api.StudyU_Flow.persistence.entity.StudentSubjectRecordEntity;
import com.api.StudyU_Flow.persistence.mapper.EvaluationMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EvaluationEntityRepository {

    private final CrudEvaluationRepository crudEvaluationRepository;
    private final CrudStudentSubjectRecordRepository crudStudentSubjectRecordRepository;
    private final EvaluationMapper evaluationMapper;

    public EvaluationEntityRepository(CrudEvaluationRepository crudEvaluationRepository, CrudStudentSubjectRecordRepository crudStudentSubjectRecordRepository, EvaluationMapper evaluationMapper) {
        this.crudEvaluationRepository = crudEvaluationRepository;
        this.crudStudentSubjectRecordRepository = crudStudentSubjectRecordRepository;
        this.evaluationMapper = evaluationMapper;
    }

    public EvaluationResponseDto add(Long idRecord, EvaluationRequestDto evaluationRequestDto) {

        if (this.crudStudentSubjectRecordRepository.findFirstByIdRecord(idRecord) == null){
            throw new SubjectRecordDoesNotExistsException(idRecord);
        }

        EvaluationEntity evaluationEntity = this.evaluationMapper.toEntity(evaluationRequestDto);
        StudentSubjectRecordEntity recordEntity = this.crudStudentSubjectRecordRepository.findFirstByIdRecord(idRecord);
        evaluationEntity.setRecord(recordEntity);

        return this.evaluationMapper.toResponseDto(this.crudEvaluationRepository.save(evaluationEntity));

    }

    public EvaluationResponseDto update(Long idEvaluation, EvaluationRequestDto evaluationRequestDto) {
        if (this.crudEvaluationRepository.findFirstByIdEvaluation(idEvaluation) == null){
            throw new EvaluationDoesNotExistsException(idEvaluation);
        }

        EvaluationEntity evaluationEntity = this.crudEvaluationRepository.findFirstByIdEvaluation(idEvaluation);

        this.evaluationMapper.updateEntityFromDto(evaluationRequestDto, evaluationEntity);

        return this.evaluationMapper.toResponseDto(this.crudEvaluationRepository.save(evaluationEntity));
    }

    public Void deleteByIdEvaluation(Long idEvaluation) {
        if (this.crudEvaluationRepository.findFirstByIdEvaluation(idEvaluation) == null){
            throw new EvaluationDoesNotExistsException(idEvaluation);
        }

        return this.crudEvaluationRepository.deleteByIdEvaluation(idEvaluation);
    }

    public EvaluationResponseDto getByIdEvaluation(Long idEvaluation) {
        if (this.crudEvaluationRepository.findFirstByIdEvaluation(idEvaluation) == null){
            throw new EvaluationDoesNotExistsException(idEvaluation);
        }

        return this.evaluationMapper.toResponseDto(this.crudEvaluationRepository.findFirstByIdEvaluation(idEvaluation));
    }

    public List<EvaluationResponseDto> getAllByIdRecord(Long idRecord) {
        if(this.crudStudentSubjectRecordRepository.findFirstByIdRecord(idRecord) == null){
            throw new SubjectRecordDoesNotExistsException(idRecord);
        }

        return this.evaluationMapper.toResponseDto(this.crudEvaluationRepository.getAllByRecord_IdRecord(idRecord));
    }
}
