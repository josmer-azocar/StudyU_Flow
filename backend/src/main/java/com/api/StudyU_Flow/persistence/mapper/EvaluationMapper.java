package com.api.StudyU_Flow.persistence.mapper;

import com.api.StudyU_Flow.domain.dto.request.EvaluationRequestDto;
import com.api.StudyU_Flow.domain.dto.response.EvaluationResponseDto;
import com.api.StudyU_Flow.persistence.entity.EvaluationEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {

    EvaluationResponseDto toResponseDto(EvaluationEntity entity);
    List<EvaluationResponseDto> toResponseDto(Iterable<EvaluationEntity> entities);

    @InheritInverseConfiguration
    EvaluationEntity toEntity(EvaluationRequestDto requestDto);
}
