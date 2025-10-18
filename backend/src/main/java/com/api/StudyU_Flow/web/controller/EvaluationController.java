package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.request.EvaluationRequestDto;
import com.api.StudyU_Flow.domain.dto.response.EvaluationResponseDto;
import com.api.StudyU_Flow.domain.service.EvaluationService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @PostMapping("/{idRecord}")
    ResponseEntity<EvaluationResponseDto> add(
            @PathVariable Long idRecord,
            @RequestBody @Valid EvaluationRequestDto evaluationRequestDto){

        return ResponseEntity.ok(this.evaluationService.add(idRecord,evaluationRequestDto));
    }

    @PutMapping("/update/{idEvaluation}")
    ResponseEntity<EvaluationResponseDto> update(
            @PathVariable Long idEvaluation,
            @RequestBody @Valid EvaluationRequestDto evaluationRequestDto){
     return ResponseEntity.ok(this.evaluationService.update(idEvaluation, evaluationRequestDto));
    }

    @GetMapping("/{idEvaluation}")
    ResponseEntity<EvaluationResponseDto> getByIdEvaluation(@PathVariable Long idEvaluation){
        return ResponseEntity.ok(this.evaluationService.getByIdEvaluation(idEvaluation));
    }

    @GetMapping("/{idRecord}")
    ResponseEntity<List<EvaluationResponseDto>> getAllByIdRecord(@PathVariable Long idRecord){

        List<EvaluationResponseDto> evaluations = this.evaluationService.getAllByIdRecord(idRecord);

        if(evaluations.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(evaluations);
    }

    @DeleteMapping("/{idEvaluation}")
    ResponseEntity<Void> deleteByIdEvaluation(@PathVariable Long idEvaluation){
        this.evaluationService.deleteByIdEvaluation(idEvaluation);
        return ResponseEntity.ok().build();
    }

}
