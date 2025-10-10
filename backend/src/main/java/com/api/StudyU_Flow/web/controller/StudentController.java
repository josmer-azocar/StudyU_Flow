package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.response.StudentResponseDto;
import com.api.StudyU_Flow.domain.dto.update.UpdateStudentDto;
import com.api.StudyU_Flow.domain.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@Tag(name = "Student", description = "CRUD Student and additional information")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<StudentResponseDto> getByUsername(@PathVariable String username){
        StudentResponseDto studentDto = this.studentService.getByUsername(username);

        if (studentDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentDto);
    }

    @PutMapping("/{username}")
    public ResponseEntity<StudentResponseDto> update(@PathVariable String username, @RequestBody @Valid UpdateStudentDto updateStudentDto){
        return ResponseEntity.ok(this.studentService.update(username, updateStudentDto));
    }
}
