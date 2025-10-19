package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.dto.response.DegreeResponseDto;
import com.api.StudyU_Flow.domain.dto.response.StudentDegreeResponseDto;
import com.api.StudyU_Flow.persistence.impl_repository.DegreeEntityRepository;
import com.api.StudyU_Flow.persistence.impl_repository.StudentDegreeEntityRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DegreeService {

    private final DegreeEntityRepository degreeEntityRepository;
    private final StudentDegreeEntityRepository studentDegreeEntityRepository;

    public DegreeService(DegreeEntityRepository degreeEntityRepository, StudentDegreeEntityRepository studentDegreeEntityRepository) {
        this.degreeEntityRepository = degreeEntityRepository;
        this.studentDegreeEntityRepository = studentDegreeEntityRepository;
    }

    public List<DegreeResponseDto> getAll(){
        return this.degreeEntityRepository.getAll();
    }

    public DegreeResponseDto getByIdDegree(Long idDegree) {
        return this.degreeEntityRepository.getByIdDegree(idDegree);
    }

    @Tool(description = "obtiene la informacion fundamental de la carrera segun el idStudentDegree")
    public DegreeResponseDto getDegreeByIdStudentDegree(@ToolParam(description = "idStudentDegree") Long idStudentDegree){
        StudentDegreeResponseDto studentDegree = this.studentDegreeEntityRepository.getByIdStudentDegree(idStudentDegree);
        return getByIdDegree(studentDegree.idDegree());
    }
}
