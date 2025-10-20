package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.request.EvaluationRequestDto;
import com.api.StudyU_Flow.domain.dto.response.EvaluationResponseDto;
import com.api.StudyU_Flow.domain.service.EvaluationService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluation")
@Tag(name = "Evaluations", description = "CRUD Evaluation and additional information")
public class EvaluationController {
    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    /**
     * @description
     * ES: Endpoint para agregar una nueva Evaluación asociada a un record.
     * EN: Endpoint to add a new Evaluation associated with a record.
     */
    @PostMapping("/{idRecord}")
    @Operation(
            summary = "Add Evaluation",
            description = "Creates a new Evaluation for the given record id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evaluation created successfully")
            }
    )
    ResponseEntity<EvaluationResponseDto> add(
            @Parameter(description = "Record id") @PathVariable Long idRecord,
            @RequestBody @Valid EvaluationRequestDto evaluationRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.evaluationService.add(idRecord,evaluationRequestDto));
    }

    /**
     * @description
     * ES: Endpoint para actualizar una Evaluación por su id.
     * EN: Endpoint to update an Evaluation by its id.
     */
    @PutMapping("/update/{idEvaluation}")
    @Operation(
            summary = "Update Evaluation",
            description = "Updates an existing Evaluation by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evaluation updated successfully")
            }
    )
    ResponseEntity<EvaluationResponseDto> update(
            @Parameter(description = "Evaluation id") @PathVariable Long idEvaluation,
            @RequestBody @Valid EvaluationRequestDto evaluationRequestDto){
     return ResponseEntity.ok(this.evaluationService.update(idEvaluation, evaluationRequestDto));
    }

    /**
     * @description
     * ES: Endpoint para obtener una Evaluación por su id.
     * EN: Endpoint to get an Evaluation by its id.
     */
    @GetMapping("/{idEvaluation}")
    @Operation(
            summary = "Get Evaluation by id",
            description = "Returns an EvaluationResponseDto for the given evaluation id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evaluation found")
            }
    )
    ResponseEntity<EvaluationResponseDto> getByIdEvaluation(
            @Parameter(description = "Evaluation id") @PathVariable Long idEvaluation){
        return ResponseEntity.ok(this.evaluationService.getByIdEvaluation(idEvaluation));
    }

    /**
     * @description
     * ES: Endpoint para obtener todas las Evaluaciones asociadas a un record por el id del record.
     * EN: Endpoint to get all Evaluations for a given record id.
     */
    @GetMapping("/record/{idRecord}")
    @Operation(
            summary = "Get all Evaluations by record id",
            description = "Returns a list of EvaluationResponseDto for the given record id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evaluations found"),
                    @ApiResponse(responseCode = "204", description = "No evaluations found for the given record id")
            }
    )
    ResponseEntity<List<EvaluationResponseDto>> getAllByIdRecord(
            @Parameter(description = "Record id") @PathVariable Long idRecord){

        List<EvaluationResponseDto> evaluations = this.evaluationService.getAllByIdRecord(idRecord);

        if(evaluations.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(evaluations);
    }

    /**
     * @description
     * ES: Endpoint para eliminar una Evaluación por su id.
     * EN: Endpoint to delete an Evaluation by its id.
     */
    @DeleteMapping("/{idEvaluation}")
    @Operation(
            summary = "Delete Evaluation by id",
            description = "Deletes the Evaluation for the given id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evaluation deleted successfully")
            }
    )
    ResponseEntity<Void> deleteByIdEvaluation(
            @Parameter(description = "Evaluation id") @PathVariable Long idEvaluation){
        this.evaluationService.deleteByIdEvaluation(idEvaluation);
        return ResponseEntity.ok().build();
    }

}
