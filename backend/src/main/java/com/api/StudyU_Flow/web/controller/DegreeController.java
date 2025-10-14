package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.response.DegreeResponseDto;
import com.api.StudyU_Flow.domain.service.DegreeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    /**
     * @description
     * ES: Endpoint para obtener todos los grados disponibles en el sistema.
     * EN: Endpoint to get all Degrees available in the system.
     */
    @GetMapping
    @Operation(
            summary = "Get all Degrees",
            description = "return List<DegreeResponseDto>",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Degrees retrieved successfully"),
                    @ApiResponse(responseCode = "204", description = "No degrees found")
            }
    )
    public ResponseEntity<List<DegreeResponseDto>> getAll(){
        List<DegreeResponseDto> degreeResponseDtos = this.degreeService.getAll();
        if(degreeResponseDtos.isEmpty()){
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(degreeResponseDtos);
    }


}
