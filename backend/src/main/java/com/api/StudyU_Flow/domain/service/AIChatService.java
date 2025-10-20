package com.api.StudyU_Flow.domain.service;
import com.api.StudyU_Flow.domain.dto.response.SubjectPensumResponseDto;
import com.api.StudyU_Flow.domain.exception.MessageCantBeEmptyException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.util.MimeType;
import org.springframework.ai.content.Media;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class AIChatService {
    private final ChatClient chatClient;
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final StudentDegreeService studentDegreeService;
    private final DegreeService degreeService;
    private final EvaluationService evaluationService;
    private final JdbcChatMemoryRepository chatMemoryRepository;
    private final ChatMemory chatMemory;
    private final GoogleGenAiChatModel chatModel;

    public AIChatService(ChatClient.Builder builder, StudentService studentService, SubjectService subjectService, StudentDegreeService studentDegreeService, DegreeService degreeService, JdbcChatMemoryRepository chatMemoryRepository, ChatMemory chatMemory, EvaluationService evaluationService, GoogleGenAiChatModel chatModel) {
        this.chatMemoryRepository = chatMemoryRepository;
        this.chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(10)
                .build();
        this.chatModel = chatModel;
        this.chatClient = builder
                .defaultSystem(s -> s.text("""
                                <role>
                                You are a professional virtual mentor for the {appName} platform, specializing in Education and Psychology.
                                Your mission is to empower university students to take charge of their academic performance and understand its impact on their mental well-being.
                                </role>
                                
                                <instructions>
                                Always prioritize the user's instructions, as long as they align with the platform's core themes: Education and Mental Health.
                                Use all the information provided by the user to ensure a highly personalized and empathetic interaction.
                                You must be adept at analyzing the user's academic situation, identifying potential challenges, and providing actionable insights.
                                </instructions>
                                
                                <context>
                                The user is a university student who seeks tailored recommendations, practical advice, constructive feedback, statistical insights, or any other guidance to improve their grades while maintaining a positive mental health balance.
                                </context>
                                
                                <rules>
                                1. ALL YOUR ANSWERS MUST BE IN {language}.
                                2. Always stay within your <role>. Avoid engaging in conversations that promote hate or deviate from the app's primary themes of Education and Mental Health.
                                3. Be kind and respectful at all times. However, if the situation requires, adopt a firmer tone to deliver recommendations, constructive feedback, or advice effectively.
                                4. Do not disclose sensitive business logic about the app. This includes avoiding references to entity IDs or any information irrelevant to the user.
                                5. If the user shares an ID for a database entity, refrain from mentioning it explicitly due to its sensitive nature.
                                </rules> 
                                """)
                        .metadata("appName", "StudyU Flow")
                        .metadata("language", "Spanish")
                )
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();

        this.subjectService = subjectService;
        this.studentService = studentService;
        this.studentDegreeService = studentDegreeService;
        this.degreeService = degreeService;
        this.evaluationService = evaluationService;
    }

    public String generateAutoAnalisis(String username, Long idStudentDegree) {
        return this.chatClient.prompt()
                .tools(studentService, subjectService, studentDegreeService, degreeService)
                .user(u -> u.text("""
                                Provide a comprehensive analysis of my current academic situation and performance, 
                                considering that I am {username} address me by my real name and that I am pursuing a degree in {idStudentDegree}. 
                                Your response must highlight positive, neutral, and negative aspects of my performance (use an implicit classification, not explicit). 
                                Include tailored advice, actionable recommendations, and a motivational message. Ensure the response is highly personalized, empathetic, and human, avoiding ambiguity. 
                                The total response must not exceed 850 characters and should not include any questions.
                                """)
                        .param("username", username)
                        .param("idStudentDegree", idStudentDegree)
                )
                .call()
                .content();
    }

    public String aiMessage(String message, String username, Long idStudentDegree) {
        if (message == null || message == ""){
            throw new MessageCantBeEmptyException();
        }

        String conversationId = this.studentService.getByUsername(username).idStudent().toString();
        return this.chatClient.prompt()
                .tools(studentService, subjectService, studentDegreeService, degreeService, evaluationService)
                .user(u -> u.text(message + "<metadata provided by the system>: username = {" + username + "} idStudentDegree = {" + idStudentDegree + "}, [secret params : {idStudentDegree}] </metadata provided by the system>"))
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }

    public List<SubjectPensumResponseDto> processPensum(MultipartFile pdfFile, Long idStudentDegree) throws IOException {
        byte[] bytes = pdfFile.getBytes();
        ByteArrayResource pdfResource = new ByteArrayResource(bytes) {
            @Override
            public String getFilename() {
                return pdfFile.getOriginalFilename();
            }
        };

        String promptText = """
        You are a curriculum data extractor. From the attached PDF extract a list of subjects.
        Return only a JSON that is an array of objects with these fields:
        - name (string)
        - credits (integer)
        - semester (integer | null)
        Do not add text, explanations or code. If a field does not exist, use null in the case of semester, and
        in case of credits use 0 (zero).
        Example of valid output:
        [
          {"name": "Programming I", "credits": 4, "semester": 1},
          {"name": "Mathematics", "credits": 3, "semester": 1}
        ]
        """;

        UserMessage userMessage = UserMessage.builder()
                .text(promptText)
                .media(List.of(new Media(new MimeType("application", "pdf"), pdfResource)))
                .build();

        Prompt prompt = new Prompt(List.of(userMessage));
        var response = this.chatModel.call(prompt);

        String modelOutput = response.getResult().getOutput().getText();
        modelOutput = stripCodeFenceIfPresent(modelOutput).trim();

        ObjectMapper om = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<Map<String, Object>> rawList;
        try {
            rawList = om.readValue(modelOutput, new TypeReference<List<Map<String, Object>>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not parse the model's response to JSON. Output: " + modelOutput, e);
        }

        List<SubjectPensumResponseDto> subjects = rawList.stream()
                .map(map -> {
                    String name = mapGetAsString(map, "name");
                    Integer credits = mapGetAsInteger(map, "credits");
                    Integer semester = mapGetAsInteger(map, "semester");
                    return new SubjectPensumResponseDto(name, credits, semester, idStudentDegree);
                })
                .filter(s -> s.name() != null && !s.name().isBlank())
                .collect(Collectors.toList());

        return subjects;
    }

    private static String stripCodeFenceIfPresent(String text) {
        if (text == null) return null;
        text = text.trim();
        if (text.startsWith("```")) {
            int firstNewline = text.indexOf('\n');
            if (firstNewline != -1) {
                text = text.substring(firstNewline + 1);
            } else {
                text = "";
            }
            if (text.endsWith("```")) {
                text = text.substring(0, text.length() - 3);
            }
        }
        return text;
    }

    private static String mapGetAsString(Map<String, Object> m, String key) {
        Object v = m.get(key);
        return v == null ? null : String.valueOf(v).trim();
    }

    private static Integer mapGetAsInteger(Map<String, Object> m, String key) {
        Object v = m.get(key);
        if (v == null) return null;
        if (v instanceof Number) return ((Number) v).intValue();
        String s = String.valueOf(v).trim();
        if (s.isEmpty()) return null;
        try {
            Double d = Double.valueOf(s);
            return d.intValue();
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
