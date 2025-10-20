package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.exception.MessageCantBeEmptyException;
import com.api.StudyU_Flow.domain.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@Tag(name = "Chat", description = "AI chat using gemini api")
public class AIChatController {

    private final AIChatService aiChatService;

    public AIChatController(AIChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    /**
     * @description
     * ES: Endpoint para generar un análisis automático para un estudiante según su username e id de la Carrera cursada.
     * EN: Endpoint to generate an automatic analysis for a student by username and student degree ID.
     */
    @GetMapping
    @Operation(
            summary = "Generate automatic analysis",
            description = "Returns an AI-generated analysis as String",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Analysis generated successfully")
            }
    )
    public ResponseEntity<String> generateAutoAnalisis(
            @Parameter(description = "username") @RequestParam String username,
            @Parameter(description = "id StudentDegree") @RequestParam Long idStudentDegree) {
        return ResponseEntity.ok(this.aiChatService.generateAutoAnalisis(username, idStudentDegree));
    }

    /**
     * @description
     * ES: Endpoint para enviar un mensaje al chat AI y recibir una respuesta.
     * EN: Endpoint to send a message to the AI chat and receive a response.
     */
    @PostMapping
    @Operation(
            summary = "Send message to AI chat",
            description = "Sends a message to the AI and returns the AI response as String",
            responses = {
                    @ApiResponse(responseCode = "200", description = "AI response returned successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - message can't be empty")
            }
    )
    public ResponseEntity<String> aiMessage(
            @Parameter(description = "Message to send to AI") @RequestBody String message,
            @Parameter(description = "username") @RequestParam String username,
            @Parameter(description = "id StudentDegree") @RequestParam Long idStudentDegree) {
        try {
            String aiResponse = this.aiChatService.aiMessage(message, username, idStudentDegree);
            return ResponseEntity.ok(aiResponse);
        } catch (MessageCantBeEmptyException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
