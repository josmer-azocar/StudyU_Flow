package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.response.DegreeCurrentProgressDto;
import com.api.StudyU_Flow.domain.service.GradeStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/statistics")
@Tag(name = "Student Statistics", description = "Statistics and information based on the Student grades")
public class GradeStatisticsController {

    private final GradeStatisticsService gradeStatisticsService;

    public GradeStatisticsController(GradeStatisticsService gradeStatisticsService) {
        this.gradeStatisticsService = gradeStatisticsService;
    }

    /**
     * @description
     * ES: Endpoint para obtener el progreso actual de la carrera de un estudiante.
     * EN: Endpoint to get the current progress of a student's degree.
     */
    @GetMapping("/currentProgress/{idStudentDegree}")
    @Operation(
            summary = "Get Degree Current Progress",
            description = "return DegreeCurrentProgressDto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Degree current progress retrieved successfully")
            }
    )
    ResponseEntity<DegreeCurrentProgressDto> getDegreeCurrentProgress(
            @Parameter(description = "id StudentDegree") @PathVariable Long idStudentDegree){
        return ResponseEntity.ok(this.gradeStatisticsService.getDegreeCurrentProgress(idStudentDegree));
    }
}
