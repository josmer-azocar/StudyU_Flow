package com.api.StudyU_Flow.persistence.mapper;

import com.api.StudyU_Flow.domain.dto.request.DegreeRequestDto;
import com.api.StudyU_Flow.domain.dto.response.DegreeResponseDto;
import com.api.StudyU_Flow.persistence.entity.DegreeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DegreeMapper {

    DegreeResponseDto toResponseDto(DegreeEntity entity);
    List<DegreeResponseDto> toResponseDto(Iterable<DegreeEntity> entities);

    @InheritInverseConfiguration
    DegreeEntity toEntity(DegreeRequestDto requestDto);
}
