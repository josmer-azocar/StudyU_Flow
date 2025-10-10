package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.response.DegreeResponseDto;
import com.api.StudyU_Flow.domain.service.DegreeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/degrees")
@Tag(name = "Degrees", description = "Information about the Degrees on the System")
public class DegreeController {

    private final DegreeService degreeService;

    public DegreeController(DegreeService degreeService) {
        this.degreeService = degreeService;
    }

    @GetMapping
    public ResponseEntity<List<DegreeResponseDto>> getAll(){
        return ResponseEntity.ok(this.degreeService.getAll());
    }


}
