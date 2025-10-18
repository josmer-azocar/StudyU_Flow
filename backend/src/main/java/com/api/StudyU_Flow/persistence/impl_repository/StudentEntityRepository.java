package com.api.StudyU_Flow.persistence.impl_repository;

import com.api.StudyU_Flow.domain.dto.response.StudentResponseDto;
import com.api.StudyU_Flow.domain.dto.update.UpdateStudentDto;
import com.api.StudyU_Flow.domain.exception.StudentDoesNotExistsException;
import com.api.StudyU_Flow.persistence.crud_repository.CrudStudentRepository;
import com.api.StudyU_Flow.persistence.entity.StudentEntity;
import com.api.StudyU_Flow.persistence.mapper.StudentMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StudentEntityRepository {

    private final CrudStudentRepository crudStudentRepository;
    private final StudentMapper studentMapper;

    public StudentEntityRepository(CrudStudentRepository crudStudentRepository, StudentMapper studentMapper) {
        this.crudStudentRepository = crudStudentRepository;
        this.studentMapper = studentMapper;
    }

    public StudentResponseDto getByUsername(String username) {
        if (this.crudStudentRepository.findFirstByUsername(username) == null) {
            throw new StudentDoesNotExistsException(username);
        }
        return this.studentMapper.toResponseDto(this.crudStudentRepository.findByUsername(username).orElse(null));
    }

    public StudentResponseDto update(String username, UpdateStudentDto updateStudentDto) {
        if (this.crudStudentRepository.findFirstByUsername(username) == null) {
            throw new StudentDoesNotExistsException(username);
        }
        StudentEntity studentEntity = this.crudStudentRepository.findByUsername(username).orElse(null);
        this.studentMapper.updateEntityFromDto(updateStudentDto, studentEntity);

        return this.studentMapper.toResponseDto(this.crudStudentRepository.save(studentEntity));
    }

    public Void deleteByIdStudent(Long idStudent) {
        if (this.crudStudentRepository.findByIdStudent(idStudent) == null) {
            throw new StudentDoesNotExistsException(idStudent);
        }
        return this.crudStudentRepository.deleteByIdStudent(idStudent);
    }

    public StudentResponseDto getByIdStudent(Long idStudent) {
        if(crudStudentRepository.findByIdStudent(idStudent) == null){
            throw new StudentDoesNotExistsException(idStudent);
        }
        return this.studentMapper.toResponseDto(this.crudStudentRepository.findByIdStudent(idStudent));
    }
}
