package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.dto.request.StudentDegreeRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentDegreeResponseDto;
import com.api.StudyU_Flow.domain.dto.update.UpdateStudentDegreeDto;
import com.api.StudyU_Flow.persistence.impl_repository.StudentDegreeEntityRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
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

    public Void deleteByIdStudentDegree(Long idStudentDegree) {
        return this.studentDegreeEntityRepository.deleteByIdStudentDegree(idStudentDegree);
    }

    @Tool(description = "Obtains information about the Degree that the user is taking according to the idStudentDegree")
    public StudentDegreeResponseDto getByIdStudentDegree(@ToolParam(description = "idStudentDegree") Long idStudentDegree) {
        return this.studentDegreeEntityRepository.getByIdStudentDegree(idStudentDegree);
    }

    public StudentDegreeResponseDto update(Long idStudentDegree, UpdateStudentDegreeDto studentDegreeDto) {
        return this.studentDegreeEntityRepository.update(idStudentDegree, studentDegreeDto);
    }
}
