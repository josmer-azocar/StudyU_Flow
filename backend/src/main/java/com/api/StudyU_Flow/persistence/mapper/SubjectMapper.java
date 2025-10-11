package com.api.StudyU_Flow.persistence.mapper;

import com.api.StudyU_Flow.domain.dto.request.SubjectRequestDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectResponseDto;
import com.api.StudyU_Flow.persistence.entity.SubjectEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(source = "studentDegree.idStudentDegree", target = "idStudentDegree")
    SubjectResponseDto toResponseDto(SubjectEntity entity);
    List<SubjectResponseDto> toResponseDto(Iterable<SubjectEntity> entities);

    @InheritInverseConfiguration
    @Mapping(target = "studentDegree.idStudentDegree", source = "idStudentDegree")
    SubjectEntity toEntity(SubjectRequestDto requestDto);
}
