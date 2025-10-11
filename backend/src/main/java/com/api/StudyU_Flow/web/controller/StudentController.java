package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.response.StudentResponseDto;
import com.api.StudyU_Flow.domain.dto.update.UpdateStudentDto;
import com.api.StudyU_Flow.domain.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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


    /**
     * @description
     * ES: Endpoint para obtener los datos de un Estudiante por su username.
     * EN: Endpoint to get a Student by username.
     */
    @GetMapping("/{username}")
    @Operation(
            summary = "Get Student by username",
            description = "return StudentResponseDto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Student not found")
            }
    )
    public ResponseEntity<StudentResponseDto> getByUsername(
            @Parameter(description = "Student username") @PathVariable String username){
        StudentResponseDto studentDto = this.studentService.getByUsername(username);

        if (studentDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentDto);
    }

    /**
     * @description
     * ES: Endpoint para actualizar los datos de un Estudiante por su username.
     * EN: Endpoint to update a Student by username.
     */
    @PutMapping("/{username}")
    @Operation(
            summary = "Update a Student by username",
            description = "return StudentResponseDto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student updated successfully")
            }
    )
    public ResponseEntity<StudentResponseDto> update(
            @Parameter(description = "Student username") @PathVariable String username,
            @Parameter(description = "Student data to update") @RequestBody @Valid UpdateStudentDto updateStudentDto){
        return ResponseEntity.ok(this.studentService.update(username, updateStudentDto));
    }
}
