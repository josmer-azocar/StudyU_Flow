package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.dto.response.SubjectPensumResponseDto;
import com.api.StudyU_Flow.domain.service.AIChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/document")
@Tag(name = "Document", description = "Documents information handler by IA Service")
public class DocumentController {

    private final AIChatService aiChatService;

    public DocumentController(AIChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    /**
     * @description
     * ES: Endpoint para procesar un archivo pensum (PDF) y extraer las materias del pensum asociadas a una Carrera cursada.
     * EN: Endpoint to process a pensum PDF file and return the extracted subjects for a given student degree.
     */
    @PostMapping("/pensum")
    @Operation(
            summary = "Process pensum PDF",
            description = "Processes a uploaded pensum file and returns a list of SubjectPensumResponseDto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pensum processed successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid file or parameters"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<List<SubjectPensumResponseDto>> processPensum(
            @Parameter(description = "id StudentDegree") @RequestParam Long idStudentDegree,
            @Parameter(description = "Pensum file (PDF)") @RequestParam("file") MultipartFile file) {
        try {
            List<SubjectPensumResponseDto> pdfData = aiChatService.processPensum(file,idStudentDegree);
            return ResponseEntity.ok(pdfData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
