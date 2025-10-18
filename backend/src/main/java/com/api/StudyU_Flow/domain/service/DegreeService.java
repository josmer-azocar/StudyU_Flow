package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.dto.response.DegreeResponseDto;
import com.api.StudyU_Flow.persistence.impl_repository.DegreeEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DegreeService {

    private final DegreeEntityRepository degreeEntityRepository;

    public DegreeService(DegreeEntityRepository degreeEntityRepository) {
        this.degreeEntityRepository = degreeEntityRepository;
    }

    public List<DegreeResponseDto> getAll(){
        return this.degreeEntityRepository.getAll();
    }

    public DegreeResponseDto getByIdDegree(Long idDegree) {
        return this.degreeEntityRepository.getByIdDegree(idDegree);
    }
}
