package com.api.StudyU_Flow.persistence.impl_repository;

import com.api.StudyU_Flow.domain.dto.request.StudentSubjectRecordRequestDto;
import com.api.StudyU_Flow.domain.dto.request.SubjectAndRecordRequestDto;
import com.api.StudyU_Flow.domain.dto.request.SubjectRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentSubjectRecordResponseDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectAndRecordResponseDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectResponseDto;
import com.api.StudyU_Flow.domain.exception.DegreeDoesNotExistsException;
import com.api.StudyU_Flow.domain.exception.StudentAlreadyExistsException;
import com.api.StudyU_Flow.domain.exception.StudentDoesNotExistsException;
import com.api.StudyU_Flow.persistence.crud_repository.CrudStudentDegreeRepository;
import com.api.StudyU_Flow.persistence.crud_repository.CrudStudentRepository;
import com.api.StudyU_Flow.persistence.crud_repository.CrudStudentSubjectRecordRepository;
import com.api.StudyU_Flow.persistence.crud_repository.CrudSubjectRepository;
import com.api.StudyU_Flow.persistence.entity.StudentDegreeEntity;
import com.api.StudyU_Flow.persistence.entity.StudentEntity;
import com.api.StudyU_Flow.persistence.entity.StudentSubjectRecordEntity;
import com.api.StudyU_Flow.persistence.entity.SubjectEntity;
import com.api.StudyU_Flow.persistence.mapper.StudentSubjectRecordMapper;
import com.api.StudyU_Flow.persistence.mapper.SubjectMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SubjectEntityRepository {

    private final SubjectMapper subjectMapper;
    private final StudentSubjectRecordMapper recordMapper;
    private final CrudSubjectRepository crudSubjectRepository;
    private final CrudStudentRepository crudStudentRepository;
    private final CrudStudentDegreeRepository crudStudentDegreeRepository;
    private final CrudStudentSubjectRecordRepository crudStudentSubjectRecordRepository;

    public SubjectEntityRepository(SubjectMapper subjectMapper, StudentSubjectRecordMapper recordMapper, CrudSubjectRepository crudSubjectRepository, CrudStudentRepository crudStudentRepository, CrudStudentDegreeRepository crudStudentDegreeRepository, CrudStudentSubjectRecordRepository crudStudentSubjectRecordRepository) {
        this.subjectMapper = subjectMapper;
        this.recordMapper = recordMapper;
        this.crudSubjectRepository = crudSubjectRepository;
        this.crudStudentRepository = crudStudentRepository;
        this.crudStudentDegreeRepository = crudStudentDegreeRepository;
        this.crudStudentSubjectRecordRepository = crudStudentSubjectRecordRepository;
    }


    public SubjectResponseDto add(SubjectRequestDto requestDto) {
        SubjectEntity subjectEntity = this.subjectMapper.toEntity(requestDto);
        return this.subjectMapper.toResponseDto(crudSubjectRepository.save(subjectEntity));
    }

    public SubjectAndRecordResponseDto add(SubjectAndRecordRequestDto requestDto) {
        if(this.crudStudentDegreeRepository.findByIdStudentDegree(requestDto.subjectData().idStudentDegree()) == null){
            throw new DegreeDoesNotExistsException(requestDto.subjectData().idStudentDegree());
        }
        SubjectEntity subjectEntity = this.subjectMapper.toEntity(requestDto.subjectData());
        subjectEntity = crudSubjectRepository.save(subjectEntity);

        StudentSubjectRecordEntity recordEntity = this.recordMapper.toEntity(requestDto.recordData());
        StudentDegreeEntity studentDegree = this.crudStudentDegreeRepository.findByIdStudentDegree(requestDto.subjectData().idStudentDegree());
        StudentEntity student = studentDegree.getStudent();
        recordEntity.setStudent(student);
        recordEntity.setSubject(subjectEntity);

        crudStudentSubjectRecordRepository.save(recordEntity);

        SubjectAndRecordResponseDto responseDto = new SubjectAndRecordResponseDto(this.subjectMapper.toResponseDto(subjectEntity), this.recordMapper.toResponseDto(recordEntity));
        return responseDto;
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
