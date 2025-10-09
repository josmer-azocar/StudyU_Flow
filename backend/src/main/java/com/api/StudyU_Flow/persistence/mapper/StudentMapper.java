package com.api.StudyU_Flow.persistence.mapper;

import com.api.StudyU_Flow.domain.dto.request.StudentRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentResponseDto;
import com.api.StudyU_Flow.persistence.entity.StudentEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring", uses = {GenderMapper.class})
public interface StudentMapper {

    @Mapping(source ="gender", target = "gender", qualifiedByName = "stringToGender")
    StudentResponseDto toResponseDto(StudentEntity entity);
    List<StudentResponseDto> toResponseDto(Iterable<StudentEntity> entities);

    @InheritInverseConfiguration
    @Mapping(source ="gender", target = "gender", qualifiedByName = "genderToString")
    StudentEntity toEntity(StudentRequestDto requestDto);
}
