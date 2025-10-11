package com.api.StudyU_Flow.persistence.impl_repository;

import com.api.StudyU_Flow.domain.dto.request.SubjectRequestDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectResponseDto;
import com.api.StudyU_Flow.persistence.crud_repository.CrudSubjectRepository;
import com.api.StudyU_Flow.persistence.entity.SubjectEntity;
import com.api.StudyU_Flow.persistence.mapper.SubjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SubjectEntityRepository {

    private final SubjectMapper subjectMapper;
    private final CrudSubjectRepository crudSubjectRepository;

    public SubjectEntityRepository(SubjectMapper subjectMapper, CrudSubjectRepository crudSubjectRepository) {
        this.subjectMapper = subjectMapper;
        this.crudSubjectRepository = crudSubjectRepository;
    }


    public SubjectResponseDto add(SubjectRequestDto requestDto) {
        SubjectEntity subjectEntity = this.subjectMapper.toEntity(requestDto);
        return this.subjectMapper.toResponseDto(crudSubjectRepository.save(subjectEntity));
    }
}
