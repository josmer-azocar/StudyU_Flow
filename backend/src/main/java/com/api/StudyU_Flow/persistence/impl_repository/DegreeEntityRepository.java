package com.api.StudyU_Flow.persistence.impl_repository;

import com.api.StudyU_Flow.domain.dto.response.DegreeResponseDto;
import com.api.StudyU_Flow.persistence.crud_repository.CrudDegreeRepository;
import com.api.StudyU_Flow.persistence.mapper.DegreeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DegreeEntityRepository {

    private final CrudDegreeRepository crudDegreeRepository;
    private final DegreeMapper degreeMapper;


    public DegreeEntityRepository(CrudDegreeRepository crudDegreeRepository, DegreeMapper degreeMapper) {
        this.crudDegreeRepository = crudDegreeRepository;
        this.degreeMapper = degreeMapper;
    }

    public List<DegreeResponseDto> getAll() {
        return this.degreeMapper.toResponseDto(this.crudDegreeRepository.findAll());
    }
}
