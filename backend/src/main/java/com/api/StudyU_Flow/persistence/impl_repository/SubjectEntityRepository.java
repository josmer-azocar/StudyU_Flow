package com.api.StudyU_Flow.persistence.impl_repository;

import com.api.StudyU_Flow.domain.dto.request.StudentSubjectRecordRequestDto;
import com.api.StudyU_Flow.domain.dto.request.SubjectAndRecordRequestDto;
import com.api.StudyU_Flow.domain.dto.request.SubjectRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentSubjectRecordResponseDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectAndRecordResponseDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectResponseDto;
import com.api.StudyU_Flow.domain.exception.*;
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
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
            if (this.crudStudentDegreeRepository.findByIdStudentDegree(idDegree) == null){
                throw new DegreeDoesNotExistsException(idDegree);
        }

        return this.subjectMapper.toResponseDto(
                this.crudSubjectRepository
                        .findSubjectEntitiesByStudentDegree_StudentUsernameAndStudentDegreeIdStudentDegree(
                                username,idDegree));
    }

    public List<SubjectAndRecordResponseDto> getAllSubjectsAndRecordsByStudent(String username, Long idStudentDegree) {
        if (this.crudStudentRepository.findFirstByUsername(username) == null) {
            throw new StudentDoesNotExistsException(username);
        } else
            if (this.crudStudentDegreeRepository.findByIdStudentDegreeAndStudent_Username(idStudentDegree, username) == null){
            throw new DegreeDoesNotExistsException(idStudentDegree);
        }

        List<SubjectEntity> subjectEntities = this.crudSubjectRepository.findAllByStudentDegree_IdStudentDegree(idStudentDegree);
        List<StudentSubjectRecordEntity> subjectRecords = new ArrayList<>();
        List<SubjectAndRecordResponseDto> responseList = new ArrayList<>();

        for (SubjectEntity subject : subjectEntities){
         subjectRecords.add(this.crudStudentSubjectRecordRepository.findBySubject_IdSubject(subject.getIdSubject()));
        }

        for (StudentSubjectRecordEntity subjectRecord : subjectRecords){
            for (SubjectEntity subject : subjectEntities){
                if ((subjectRecord != null) && (subject != null)){
                if (subjectRecord.getSubject().getIdSubject().equals(subject.getIdSubject())) {
                    responseList.add(
                            new SubjectAndRecordResponseDto(
                                    this.subjectMapper.toResponseDto(subject),
                                    this.recordMapper.toResponseDto(subjectRecord)
                            )
                    );
                }
                }
            }
        }

        return responseList;
    }

    public StudentSubjectRecordResponseDto addRecordToSubject(String username, Long idSubject, StudentSubjectRecordRequestDto requestDto) {
        if (this.crudStudentRepository.findFirstByUsername(username) == null) {
            throw new StudentDoesNotExistsException(username);

        } else
        if (this.crudSubjectRepository.findByIdSubject(idSubject) == null){
            throw new SubjectDoesNotExistsException(idSubject);
        }

        StudentSubjectRecordEntity recordEntity = this.recordMapper.toEntity(requestDto);
        StudentEntity student = this.crudStudentRepository.findFirstByUsername(username);
        SubjectEntity subject = this.crudSubjectRepository.findByIdSubject(idSubject);

        if (!(subject.getStudentDegree().getStudent().getUsername().equals(username))){
            throw new SubjectDoesNotExistsException(idSubject);
        }

        recordEntity.setStudent(student);
        recordEntity.setSubject(subject);

        return this.recordMapper.toResponseDto(this.crudStudentSubjectRecordRepository.save(recordEntity));
    }

    public List<SubjectAndRecordResponseDto> getAllSubjectsAndRecordsBySemester(String username, Long idStudentDegree, Integer nSemester) {
        if (this.crudStudentRepository.findFirstByUsername(username) == null) {
            throw new StudentDoesNotExistsException(username);
        } else
        if (this.crudStudentDegreeRepository.findByIdStudentDegreeAndStudent_Username(idStudentDegree, username) == null){
            throw new DegreeDoesNotExistsException(idStudentDegree);
        }

        List<SubjectEntity> subjectEntities = this.crudSubjectRepository
                .findAllByStudentDegree_IdStudentDegreeAndSemester(idStudentDegree, nSemester);

        if(subjectEntities.isEmpty()){
            throw new SubjectsBySemesterDontExistExc(idStudentDegree, nSemester);
        }

        List<StudentSubjectRecordEntity> subjectRecords = new ArrayList<>();
        List<SubjectAndRecordResponseDto> responseList = new ArrayList<>();

        for (SubjectEntity subject : subjectEntities){
            subjectRecords.add(this.crudStudentSubjectRecordRepository.findBySubject_IdSubject(subject.getIdSubject()));
        }

        for (StudentSubjectRecordEntity subjectRecord : subjectRecords){
            for (SubjectEntity subject : subjectEntities){
                if ((subjectRecord != null) && (subject != null)){
                    if (subjectRecord.getSubject().getIdSubject().equals(subject.getIdSubject())) {
                        responseList.add(
                                new SubjectAndRecordResponseDto(
                                        this.subjectMapper.toResponseDto(subject),
                                        this.recordMapper.toResponseDto(subjectRecord)
                                )
                        );
                    }
                }
            }
        }

        return responseList;
    }

    public StudentSubjectRecordResponseDto updateRecord(Long idRecord, StudentSubjectRecordRequestDto requestDto) {
        if (this.crudStudentSubjectRecordRepository.findFirstByIdRecord(idRecord) == null){
            throw new SubjectRecordDoesNotExistsException(idRecord);
        }

        StudentSubjectRecordEntity recordEntity = this.crudStudentSubjectRecordRepository.findFirstByIdRecord(idRecord);

        this.recordMapper.updateEntityFromDto(requestDto, recordEntity);

        return this.recordMapper.toResponseDto(this.crudStudentSubjectRecordRepository.save(recordEntity));
    }

    public ResponseEntity<Void> deleteSubjectAndRecordByIdSubject(Long idSubject) {
        if (this.crudSubjectRepository.findByIdSubject(idSubject) == null){
            throw new SubjectDoesNotExistsException(idSubject);
        }

        if (this.crudStudentSubjectRecordRepository.findBySubject_IdSubject(idSubject) != null){
            this.crudStudentSubjectRecordRepository.deleteBySubject_IdSubject(idSubject);
        }

        return this.crudSubjectRepository.deleteByIdSubject(idSubject);
    }

    public SubjectAndRecordResponseDto getSubjectAndRecordByIdSubject(Long idSubject) {
        if (this.crudSubjectRepository.findByIdSubject(idSubject) == null){
            throw new SubjectDoesNotExistsException(idSubject);
        }

        SubjectEntity subjectEntity = this.crudSubjectRepository.findByIdSubject(idSubject);
        StudentSubjectRecordEntity recordEntity = this.crudStudentSubjectRecordRepository.findBySubject_IdSubject(idSubject);

            SubjectAndRecordResponseDto responseDto = new SubjectAndRecordResponseDto(
                    this.subjectMapper.toResponseDto(subjectEntity),
                    this.recordMapper.toResponseDto(recordEntity)
            );

            return responseDto;

    }
}
