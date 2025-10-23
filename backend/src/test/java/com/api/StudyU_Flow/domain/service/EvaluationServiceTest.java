package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.dto.request.EvaluationRequestDto;
import com.api.StudyU_Flow.domain.dto.response.EvaluationResponseDto;
import com.api.StudyU_Flow.persistence.impl_repository.EvaluationEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvaluationServiceTest {

    @Mock
    private EvaluationEntityRepository evaluationEntityRepository;

    @InjectMocks
    private EvaluationService evaluationService;

    EvaluationResponseDto EVALUATION_RESPONSE_DTO = new EvaluationResponseDto(
            1L,
            "virtual exam",
             8.0,
            10.0,
            15.0,
            1L
    );

    EvaluationResponseDto EVALUATION_RESPONSE2_DTO = new EvaluationResponseDto(
            1L,
            "Project",
            7.0,
            20.0,
            20.0,
            2L
    );

    EvaluationRequestDto EVALUATION_REQUEST_DTO = new EvaluationRequestDto(
            "virtual exam",
            8.0,
            10.0,
            15.0
    );


    @Test
    void add() {
        when(evaluationEntityRepository.add(anyLong(), any(EvaluationRequestDto.class))).thenReturn(EVALUATION_RESPONSE_DTO);

        EvaluationResponseDto result = evaluationService.add(1L, EVALUATION_REQUEST_DTO);

        assertNotEquals(EVALUATION_RESPONSE2_DTO, result);
        assertNotEquals(EVALUATION_RESPONSE2_DTO.evaluationName(), result.evaluationName());
    }

    @Test
    void update() {
        when(evaluationEntityRepository.update(1L, EVALUATION_REQUEST_DTO)).thenReturn(EVALUATION_RESPONSE_DTO);

        EvaluationResponseDto result = evaluationService.update(1L, EVALUATION_REQUEST_DTO);

        assertNotEquals(EVALUATION_RESPONSE2_DTO, result);
    }

    @Test
    void deleteByIdEvaluation() {
        doNothing().when(evaluationEntityRepository).deleteByIdEvaluation(1L);

        assertDoesNotThrow(() -> evaluationService.deleteByIdEvaluation(1L));
    }

    @Test
    void getByIdEvaluation() {
        when(evaluationEntityRepository.getByIdEvaluation(1L)).thenReturn(EVALUATION_RESPONSE_DTO);

        EvaluationResponseDto result = evaluationService.getByIdEvaluation(1L);

        assertNotEquals(EVALUATION_RESPONSE2_DTO, result);
    }

    @Test
    void getAllByIdRecord() {
        List<EvaluationResponseDto> evaluations = List.of(EVALUATION_RESPONSE_DTO);
        when(evaluationEntityRepository.getAllByIdRecord(1L)).thenReturn(evaluations);

        List<EvaluationResponseDto> result = evaluationService.getAllByIdRecord(1L);

        assertEquals(evaluations, result);
    }

    @Test
    void getAllByIdStudentDegree() {
        List<EvaluationResponseDto> evaluations = List.of(EVALUATION_RESPONSE_DTO);
        when(evaluationEntityRepository.getAllByIdStudentDegree(1L)).thenReturn(evaluations);

        List<EvaluationResponseDto> result = evaluationService.getAllByIdStudentDegree(1L);

        assertEquals(evaluations, result);
    }
}