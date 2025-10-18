package com.api.StudyU_Flow.persistence.crud_repository;

import com.api.StudyU_Flow.persistence.entity.DegreeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudDegreeRepository extends CrudRepository<DegreeEntity, Long> {

    DegreeEntity findByIdDegree(Long idDegree);

    DegreeEntity findFirstByIdDegree(Long idDegree);
}

