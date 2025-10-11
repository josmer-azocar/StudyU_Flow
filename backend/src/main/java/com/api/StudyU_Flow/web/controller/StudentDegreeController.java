package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.request.StudentDegreeRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentDegreeResponseDto;
import com.api.StudyU_Flow.domain.service.StudentDegreeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student_degrees")
@Tag(name = "Student Degrees", description = "Information about the Student Degrees on the System")
public class StudentDegreeController {

    private final StudentDegreeService studentDegreeService;

    public StudentDegreeController(StudentDegreeService studentDegreeService) {
        this.studentDegreeService = studentDegreeService;
    }

    /**
     * @description
     * ES: Endpoint para registrar un grado académico a un Estudiante por su username.
     * EN: Endpoint to add an academic degree to a Student by username.
     */
    @PostMapping("/{username}")
    @Operation(
            summary = "Add a Degree to a Student by username",
            description = "return StudentDegreeResponseDto",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Student Degree created successfully")
            }
    )
    public ResponseEntity<StudentDegreeResponseDto> addStudentDegree(
            @Parameter(description = "Student username") @PathVariable String username,
            @Parameter(description = "Student Degree data") @RequestBody StudentDegreeRequestDto studentDegreeRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.studentDegreeService.add(username, studentDegreeRequestDto));
    }

    /**
     * @description
     * ES: Endpoint para obtener todos los grados académicos de un Estudiante por su username.
     * EN: Endpoint to get all academic degrees of a Student by username.
     */
    @GetMapping("/{username}")
    @Operation(
            summary = "Get all Degrees by Student username",
            description = "return List<StudentDegreeResponseDto>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Student Degrees retrieved successfully")
            }
    )
    public ResponseEntity<List<StudentDegreeResponseDto>> getAllByUsername(
            @Parameter(description = "Student username") @PathVariable String username){
        return ResponseEntity.ok(this.studentDegreeService.getAllByUsername(username));
    }
}
