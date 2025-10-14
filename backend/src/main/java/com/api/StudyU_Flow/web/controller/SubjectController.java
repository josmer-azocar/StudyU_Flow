package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.request.SubjectRequestDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectResponseDto;
import com.api.StudyU_Flow.domain.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
@Tag(name = "Subjects", description = "CRUD Subject and additional information")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * @description
     * ES: Endpoint para crear una nueva Asignatura.
     * EN: Endpoint to create a new Subject.
     */
    @PostMapping
    @Operation(
            summary = "Create a new Subject",
            description = "return SubjectResponseDto",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Subject created successfully")
            }
    )
    ResponseEntity<SubjectResponseDto> add(
            @Parameter(description = "Subject data")
            @RequestBody @Valid SubjectRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.subjectService.add(requestDto));
}

    /**
     * @description
     * ES: Endpoint para obtener todas las Asignaturas de un estudiante por su nombre de usuario y el id de la carrera que cursa.
     * EN: Endpoint to get all Subjects for a student by username and Student degree id.
     */
    @GetMapping("/{username}/{idStudentDegree}")
    @Operation(
            summary = "Get all Subjects by student",
            description = "Returns a list of SubjectResponseDto for a given student and degree id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Subjects found"),
                    @ApiResponse(responseCode = "204", description = "No subjects found")
            }
    )
    ResponseEntity<List<SubjectResponseDto>> getAllByStudent(
            @Parameter(description = "Student username")
            @PathVariable String username,
            @Parameter(description = "StudentDegree id")
            @PathVariable Long idStudentDegree){

        List<SubjectResponseDto> subjectResponseDtos = this.subjectService.getAllByStudent(username, idStudentDegree);

        if(subjectResponseDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(subjectResponseDtos);
    }

}
