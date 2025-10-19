package com.api.StudyU_Flow.domain.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIChatService {
    private final ChatClient chatClient;
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final StudentDegreeService studentDegreeService;
    private final DegreeService degreeService;

    public AIChatService(ChatClient.Builder builder, StudentService studentService, SubjectService subjectService, StudentDegreeService studentDegreeService, DegreeService degreeService) {
        this.chatClient = builder
                .defaultSystem(s -> s.text("""
                                <rol>
                                Eres un mentor virtual de la plataforma "StudyU Flow", experto en el area de la Educacion y psicologia, el cual su pasion es ayudar a cualquier
                                estudiante universitario a llevar el control de su rendimiento academico y notas.
                                </rol>
                                
                                <instrucciones>
                                Debes priorizar seguir las instrucciones del usuario siempre y cuando se relacionen con el tema de la app, que es
                                la Educacion y salud mental. Toma en cuenta toda la informacion posible del usuario para que tengas un acercamiento
                                humano y personalizado.
                                Debes ser capaz de realizar analisis de la situacion academica actual del usuario y las posibles causas.
                                </instrucciones>
                                
                                <contexto>
                                El usuario es un estudiante universitario el cual esta en busca de recomendaciones, correcciones, consejos, acerca de
                                su rendimiento que lo ayuden a mejorar.
                                </contexto>
                                
                                <reglas>
                                1_ No te salgas de tu <rol>, evita conversaciones que inciten al odio y desviar el tema principal de la app.
                                2_No seas condescendiente, es importante que seas amable y respetuoso, pero si debes de tomar un tono mas recto en ciertos momentos
                                para dar recomendaciones y consejos hazlo.
                                </reglas>
                                """)
                        .metadata("appName", "StudyU Flow"))
                .build();

        this.subjectService = subjectService;
        this.studentService = studentService;
        this.studentDegreeService = studentDegreeService;
        this.degreeService = degreeService;
    }


    public String generation(String username, Long idStudentDegree) {
        return this.chatClient.prompt()
                .tools(studentService, subjectService, studentDegreeService, degreeService)
                .user(u -> u.text("Realiza un analisis General de mi situacion academica actual y mi rendimiento tomando en cuenta que soy {username} llamame por mi nombre real, y la carrera que curso es {idStudentDegree} tu respuesta debe contener aspectos positivos, neutrales " +
                                "y negativos sobre mi rendimiento (haz una clasificacion implicita, no explicita), ademas de consejos y recomendaciones, y mensaje motivacional, tu respuesta debe ser de maximo 850 caracteres y no debe contener preguntas, tu acercamiento debe ser lo mas personalizado posible no algo ambiguo")
                        .param("username", username)
                        .param("idStudentDegree", idStudentDegree)
                )
                .call()
                .content();
    }
}
