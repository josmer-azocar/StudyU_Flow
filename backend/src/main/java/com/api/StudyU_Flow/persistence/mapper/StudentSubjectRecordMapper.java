package com.api.StudyU_Flow.persistence.mapper;

import com.api.StudyU_Flow.domain.dto.request.StudentSubjectRecordRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentSubjectRecordResponseDto;
import com.api.StudyU_Flow.persistence.entity.StudentSubjectRecordEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StatusMapper.class})
public interface StudentSubjectRecordMapper {

    @Mapping(source ="status", target = "status", qualifiedByName = "stringToStatus")
    @Mapping(source = "student.idStudent", target = "idStudent")
    @Mapping(source = "subject.idSubject", target = "idSubject")
    StudentSubjectRecordResponseDto toResponseDto(StudentSubjectRecordEntity entity);
    List<StudentSubjectRecordResponseDto> toResponseDto(Iterable<StudentSubjectRecordEntity> entities);

    @InheritInverseConfiguration
    @Mapping(source ="status", target = "status", qualifiedByName = "statusToString")
    StudentSubjectRecordEntity toEntity(StudentSubjectRecordRequestDto requestDto);

    void updateEntityFromDto(StudentSubjectRecordRequestDto updateDto, @MappingTarget StudentSubjectRecordEntity recordEntity);
}
