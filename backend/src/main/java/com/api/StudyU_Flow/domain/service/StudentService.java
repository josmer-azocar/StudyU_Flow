package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.dto.response.StudentResponseDto;
import com.api.StudyU_Flow.domain.dto.update.UpdateStudentDto;
import com.api.StudyU_Flow.persistence.impl_repository.StudentEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentEntityRepository studentEntityRepository;

    public StudentService(StudentEntityRepository studentEntityRepository) {
        this.studentEntityRepository = studentEntityRepository;
    }

    public StudentResponseDto getByUsername(String username) {
        return this.studentEntityRepository.getByUsername(username);
    }

    public StudentResponseDto update(String username, UpdateStudentDto updateStudentDto){
        return this.studentEntityRepository.update(username, updateStudentDto);
    }
}
