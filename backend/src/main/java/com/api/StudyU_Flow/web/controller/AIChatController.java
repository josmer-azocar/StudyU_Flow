package com.api.StudyU_Flow.web.controller;

import com.api.StudyU_Flow.domain.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@Tag(name = "Chat", description = "AI chat using gemini api")
public class AIChatController {

    private final AIChatService aiChatService;

    public AIChatController(AIChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @GetMapping
    public ResponseEntity<String> generation(@RequestParam String username, @RequestParam Long idStudentDegree) {
        return ResponseEntity.ok(this.aiChatService.generation(username, idStudentDegree));
    }
}
