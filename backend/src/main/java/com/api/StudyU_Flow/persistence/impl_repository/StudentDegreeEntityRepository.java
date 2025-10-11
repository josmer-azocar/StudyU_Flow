package com.api.StudyU_Flow.persistence.impl_repository;

import com.api.StudyU_Flow.domain.dto.request.StudentDegreeRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentDegreeResponseDto;
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
        StudentDegreeEntity studentDegreeEntity = this.studentDegreeMapper.toEntity(studentDegreeRequestDto);
        StudentEntity studentEntity = this.crudStudentRepository.findFirstByUsername(username);

//        //adding id student
        studentDegreeEntity.setStudent(studentEntity);
        return this.studentDegreeMapper.toResponseDto(crudStudentDegreeRepository.save(studentDegreeEntity));

    }

    public List<StudentDegreeResponseDto> getAllByUsername(String username) {
        return this.studentDegreeMapper.toResponseDto(this.crudStudentDegreeRepository.findAllByStudent_Username(username));
    }
}
