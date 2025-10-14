package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.dto.request.SubjectRequestDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectResponseDto;
import com.api.StudyU_Flow.persistence.impl_repository.SubjectEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectEntityRepository subjectEntityRepository;

    public SubjectService(SubjectEntityRepository subjectEntityRepository) {
        this.subjectEntityRepository = subjectEntityRepository;
    }

    public SubjectResponseDto add(SubjectRequestDto requestDto) {
        return this.subjectEntityRepository.add(requestDto);
    }

    public List<SubjectResponseDto> getAllByStudent(String username, Long idDegree) {
        return this.subjectEntityRepository.getAllByStudent(username, idDegree);
    }
}
