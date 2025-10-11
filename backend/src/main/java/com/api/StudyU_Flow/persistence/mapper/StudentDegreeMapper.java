package com.api.StudyU_Flow.persistence.mapper;

import com.api.StudyU_Flow.domain.dto.request.StudentDegreeRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentDegreeResponseDto;
import com.api.StudyU_Flow.persistence.entity.StudentDegreeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentDegreeMapper {

    @Mapping(source = "student.idStudent", target = "idStudent")
    @Mapping(source = "degree.idDegree", target = "idDegree")
    StudentDegreeResponseDto toResponseDto(StudentDegreeEntity entity);
    List<StudentDegreeResponseDto> toResponseDto(Iterable<StudentDegreeEntity> entities);

    @InheritInverseConfiguration
    @Mapping(target = "degree.idDegree", source = "idDegree")
    StudentDegreeEntity toEntity(StudentDegreeRequestDto requestDto);
}
