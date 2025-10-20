package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.dto.response.StudentResponseDto;
import com.api.StudyU_Flow.domain.dto.update.UpdateStudentDto;
import com.api.StudyU_Flow.persistence.impl_repository.StudentEntityRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentEntityRepository studentEntityRepository;

    public StudentService(StudentEntityRepository studentEntityRepository) {
        this.studentEntityRepository = studentEntityRepository;
    }

    @Tool(description = "Obtains the user's personal information through his/her username")
    public StudentResponseDto getByUsername(@ToolParam(description = "username") String username) {
        return this.studentEntityRepository.getByUsername(username);
    }

    public StudentResponseDto update(String username, UpdateStudentDto updateStudentDto){
        return this.studentEntityRepository.update(username, updateStudentDto);
    }

    public Void deleteByIdStudent(Long idStudent) {
        return this.studentEntityRepository.deleteByIdStudent(idStudent);
    }

    public StudentResponseDto getByIdStudent(Long idStudent) {
        return this.studentEntityRepository.getByIdStudent(idStudent);
    }
}
