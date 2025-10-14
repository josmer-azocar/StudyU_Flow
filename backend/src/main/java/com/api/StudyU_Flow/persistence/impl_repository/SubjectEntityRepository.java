package com.api.StudyU_Flow.persistence.impl_repository;

import com.api.StudyU_Flow.domain.dto.request.SubjectRequestDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectResponseDto;
import com.api.StudyU_Flow.domain.exception.DegreeDoesNotExistsException;
import com.api.StudyU_Flow.domain.exception.StudentAlreadyExistsException;
import com.api.StudyU_Flow.domain.exception.StudentDoesNotExistsException;
import com.api.StudyU_Flow.persistence.crud_repository.CrudStudentDegreeRepository;
import com.api.StudyU_Flow.persistence.crud_repository.CrudStudentRepository;
import com.api.StudyU_Flow.persistence.crud_repository.CrudSubjectRepository;
import com.api.StudyU_Flow.persistence.entity.SubjectEntity;
import com.api.StudyU_Flow.persistence.mapper.SubjectMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubjectEntityRepository {

    private final SubjectMapper subjectMapper;
    private final CrudSubjectRepository crudSubjectRepository;
    private final CrudStudentRepository crudStudentRepository;
    private final CrudStudentDegreeRepository crudStudentDegreeRepository;

    public SubjectEntityRepository(SubjectMapper subjectMapper, CrudSubjectRepository crudSubjectRepository, CrudStudentRepository crudStudentRepository, CrudStudentDegreeRepository crudStudentDegreeRepository) {
        this.subjectMapper = subjectMapper;
        this.crudSubjectRepository = crudSubjectRepository;
        this.crudStudentRepository = crudStudentRepository;
        this.crudStudentDegreeRepository = crudStudentDegreeRepository;
    }


    public SubjectResponseDto add(SubjectRequestDto requestDto) {
        SubjectEntity subjectEntity = this.subjectMapper.toEntity(requestDto);
        return this.subjectMapper.toResponseDto(crudSubjectRepository.save(subjectEntity));
    }

    public List<SubjectResponseDto> getAllByStudent(String username, Long idDegree) {
        if (this.crudStudentRepository.findFirstByUsername(username) == null) {
            throw new StudentDoesNotExistsException(username);

        } else
            if(this.crudStudentDegreeRepository.findByIdStudentDegree(idDegree) == null){
                throw new DegreeDoesNotExistsException(idDegree);
        }

        return this.subjectMapper.toResponseDto(
                this.crudSubjectRepository
                        .findSubjectEntitiesByStudentDegree_StudentUsernameAndStudentDegreeIdStudentDegree(
                                username,idDegree));
    }
}
