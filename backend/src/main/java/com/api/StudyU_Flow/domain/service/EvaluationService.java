package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.dto.request.EvaluationRequestDto;
import com.api.StudyU_Flow.domain.dto.response.EvaluationResponseDto;
import com.api.StudyU_Flow.persistence.impl_repository.EvaluationEntityRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {

    private final EvaluationEntityRepository evaluationEntityRepository;

    public EvaluationService(EvaluationEntityRepository evaluationEntityRepository) {
        this.evaluationEntityRepository = evaluationEntityRepository;
    }

    public EvaluationResponseDto add(Long idRecord, EvaluationRequestDto evaluationRequestDto) {
       return this.evaluationEntityRepository.add(idRecord, evaluationRequestDto);
    }

    public EvaluationResponseDto update(Long idEvaluation, EvaluationRequestDto evaluationRequestDto) {
        return this.evaluationEntityRepository.update(idEvaluation, evaluationRequestDto);
    }

    public Void deleteByIdEvaluation(Long idEvaluation) {
        return this.evaluationEntityRepository.deleteByIdEvaluation(idEvaluation);
    }

    public EvaluationResponseDto getByIdEvaluation(Long idEvaluation) {
        return this.evaluationEntityRepository.getByIdEvaluation(idEvaluation);
    }

    @Tool(description = "Obtains a list of subjects evaluations with their information according to the idRecord " +
            "(in order to get idRecord is important to use SubjectService to obtain data of subjects and their records)")
    public List<EvaluationResponseDto> getAllByIdRecord(@ToolParam(description = "idRecord") Long idRecord) {
        return this.evaluationEntityRepository.getAllByIdRecord(idRecord);
    }
}
