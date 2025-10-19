package com.api.StudyU_Flow.web.controller;
import com.api.StudyU_Flow.domain.dto.response.DegreeCurrentProgressDto;
import com.api.StudyU_Flow.domain.dto.response.GeneralAndWeightedAverageDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectAndRecordResponseDto;
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
import java.util.List;

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

    /**
     * @description
     * ES: Endpoint para obtener las cinco materias con mejores calificaciones de un estudiante.
     * EN: Endpoint to get the top five subjects with the best grades for a student's degree.
     */
    @GetMapping("/topFiveSubjects/{idStudentDegree}")
    @Operation(
            summary = "Get Top Five Best Subjects Grades",
            description = "return List<SubjectAndRecordResponseDto>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Top five subjects retrieved successfully"),
                    @ApiResponse(responseCode = "204", description = "No content when the student has no recorded subjects")
            }
    )
    ResponseEntity<List<SubjectAndRecordResponseDto>> getTopFiveBestSubjectsGrades(
            @Parameter(description = "id StudentDegree") @PathVariable Long idStudentDegree){
        List<SubjectAndRecordResponseDto> top5 = this.gradeStatisticsService.getTopFiveBestSubjectsGrades(idStudentDegree);
        if (top5.isEmpty()){
            return ResponseEntity.noContent().build();
        }
            return ResponseEntity.ok(top5);
    }

    /**
     * @description
     * ES: Endpoint para obtener el promedio general y el promedio ponderado de un estudiante.
     * EN: Endpoint to get the general and weighted average for a student's degree.
     */
    @GetMapping("/average/{idStudentDegree}")
    @Operation(
            summary = "Get General and Weighted Average",
            description = "return GeneralAndWeightedAverageDto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "General and weighted averages retrieved successfully")
            }
    )
    ResponseEntity<GeneralAndWeightedAverageDto> getGeneralAndWeightedAverage(
            @Parameter(description = "id StudentDegree") @PathVariable Long idStudentDegree){
        return ResponseEntity.ok(this.gradeStatisticsService.getGeneralAndWeightedAverage(idStudentDegree));
    }

}
