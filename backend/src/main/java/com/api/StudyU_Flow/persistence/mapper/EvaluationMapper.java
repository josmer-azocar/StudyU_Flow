package com.api.StudyU_Flow.persistence.mapper;

import com.api.StudyU_Flow.domain.dto.request.EvaluationRequestDto;
import com.api.StudyU_Flow.domain.dto.request.StudentSubjectRecordRequestDto;
import com.api.StudyU_Flow.domain.dto.response.EvaluationResponseDto;
import com.api.StudyU_Flow.persistence.entity.EvaluationEntity;
import com.api.StudyU_Flow.persistence.entity.StudentSubjectRecordEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {

    @Mapping(source = "record.idRecord", target = "idRecord")
    EvaluationResponseDto toResponseDto(EvaluationEntity entity);
    List<EvaluationResponseDto> toResponseDto(Iterable<EvaluationEntity> entities);

    @InheritInverseConfiguration
    EvaluationEntity toEntity(EvaluationRequestDto requestDto);

    void updateEntityFromDto(EvaluationRequestDto updateDto, @MappingTarget EvaluationEntity evaluationEntity);
}
