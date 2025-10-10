package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.request.StudentDegreeRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentDegreeResponseDto;
import com.api.StudyU_Flow.domain.service.StudentDegreeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student_degrees")
@Tag(name = "Student Degrees", description = "Information about the Student Degrees on the System")
public class StudentDegreeController {

    private final StudentDegreeService studentDegreeService;

    public StudentDegreeController(StudentDegreeService studentDegreeService) {
        this.studentDegreeService = studentDegreeService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<StudentDegreeResponseDto> addStudentDegree(
            @PathVariable String username,
            @RequestBody StudentDegreeRequestDto studentDegreeRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.studentDegreeService.add(username, studentDegreeRequestDto));
    }

}
