package com.api.StudyU_Flow.persistence.mapper;

import com.api.StudyU_Flow.domain.dto.request.SubjectRequestDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectResponseDto;
import com.api.StudyU_Flow.persistence.entity.SubjectEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    SubjectResponseDto toResponseDto(SubjectEntity entity);
    List<SubjectResponseDto> toResponseDto(Iterable<SubjectEntity> entities);

    @InheritInverseConfiguration
    SubjectEntity toEntity(SubjectRequestDto requestDto);
}
