package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.request.SubjectRequestDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectResponseDto;
import com.api.StudyU_Flow.domain.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                    @ApiResponse(responseCode = "200", description = "Subject created successfully")
            }
    )
    ResponseEntity<SubjectResponseDto> add(
            @Parameter(description = "Subject data")
            @RequestBody @Valid SubjectRequestDto requestDto){
        return ResponseEntity.ok(this.subjectService.add(requestDto));
}
}
