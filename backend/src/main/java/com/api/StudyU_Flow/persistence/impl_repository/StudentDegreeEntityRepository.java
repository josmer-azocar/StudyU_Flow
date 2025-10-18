package com.api.StudyU_Flow.persistence.impl_repository;

import com.api.StudyU_Flow.domain.dto.request.StudentDegreeRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentDegreeResponseDto;
import com.api.StudyU_Flow.domain.dto.update.UpdateStudentDegreeDto;
import com.api.StudyU_Flow.domain.exception.DegreeDoesNotExistsException;
import com.api.StudyU_Flow.domain.exception.StudentDoesNotExistsException;
import com.api.StudyU_Flow.persistence.crud_repository.CrudStudentDegreeRepository;
import com.api.StudyU_Flow.persistence.crud_repository.CrudStudentRepository;
import com.api.StudyU_Flow.persistence.entity.StudentDegreeEntity;
import com.api.StudyU_Flow.persistence.entity.StudentEntity;
import com.api.StudyU_Flow.persistence.mapper.StudentDegreeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDegreeEntityRepository {
    private final StudentDegreeMapper studentDegreeMapper;
    private final CrudStudentDegreeRepository crudStudentDegreeRepository;
    private final CrudStudentRepository crudStudentRepository;

    public StudentDegreeEntityRepository(StudentDegreeMapper studentDegreeMapper, CrudStudentDegreeRepository crudStudentDegreeRepository, CrudStudentRepository crudStudentRepository) {
        this.studentDegreeMapper = studentDegreeMapper;
        this.crudStudentDegreeRepository = crudStudentDegreeRepository;
        this.crudStudentRepository = crudStudentRepository;
    }

    public StudentDegreeResponseDto add(String username, StudentDegreeRequestDto studentDegreeRequestDto) {
        if (this.crudStudentRepository.findFirstByUsername(username) == null) {
            throw new StudentDoesNotExistsException(username);
        }

        StudentDegreeEntity studentDegreeEntity = this.studentDegreeMapper.toEntity(studentDegreeRequestDto);
        StudentEntity studentEntity = this.crudStudentRepository.findFirstByUsername(username);

        //adding id student
        studentDegreeEntity.setStudent(studentEntity);
        return this.studentDegreeMapper.toResponseDto(crudStudentDegreeRepository.save(studentDegreeEntity));

    }

    public List<StudentDegreeResponseDto> getAllByUsername(String username) {
        if (this.crudStudentRepository.findFirstByUsername(username) == null) {
            throw new StudentDoesNotExistsException(username);
        }
        return this.studentDegreeMapper.toResponseDto(this.crudStudentDegreeRepository.findAllByStudent_Username(username));
    }

    public Void deleteByIdStudentDegree(Long idStudentDegree) {
        if (this.crudStudentDegreeRepository.findByIdStudentDegree(idStudentDegree) == null){
            throw new DegreeDoesNotExistsException(idStudentDegree);
        }

        return this.crudStudentDegreeRepository.deleteByIdStudentDegree(idStudentDegree);
    }

    public StudentDegreeResponseDto getByIdStudentDegree(Long idStudentDegree) {
        if(this.crudStudentDegreeRepository.findByIdStudentDegree(idStudentDegree) == null){
            throw new DegreeDoesNotExistsException(idStudentDegree);
        }
        StudentDegreeEntity studentDegree = this.crudStudentDegreeRepository.findByIdStudentDegree(idStudentDegree);

        return this.studentDegreeMapper.toResponseDto(studentDegree);

    }

    public StudentDegreeResponseDto update(Long idStudentDegree, UpdateStudentDegreeDto studentDegreeDto) {
        if (this.crudStudentDegreeRepository.findByIdStudentDegree(idStudentDegree) == null ){
            throw new DegreeDoesNotExistsException(idStudentDegree);
        }

        StudentDegreeEntity studentDegreeEntity = this.crudStudentDegreeRepository.findByIdStudentDegree(idStudentDegree);
        this.studentDegreeMapper.updateEntityFromDto(studentDegreeDto, studentDegreeEntity);

        return this.studentDegreeMapper.toResponseDto(this.crudStudentDegreeRepository.save(studentDegreeEntity));
    }
}
