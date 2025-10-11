package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.dto.request.StudentDegreeRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentDegreeResponseDto;
import com.api.StudyU_Flow.persistence.impl_repository.StudentDegreeEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentDegreeService {
    private final StudentDegreeEntityRepository studentDegreeEntityRepository;

    public StudentDegreeService(StudentDegreeEntityRepository studentDegreeEntityRepository) {
        this.studentDegreeEntityRepository = studentDegreeEntityRepository;
    }

    public StudentDegreeResponseDto add(String username, StudentDegreeRequestDto studentDegreeRequestDto) {
        return this.studentDegreeEntityRepository.add(username, studentDegreeRequestDto);
    }

    public List<StudentDegreeResponseDto> getAllByUsername(String username) {
        return this.studentDegreeEntityRepository.getAllByUsername(username);
    }
}
