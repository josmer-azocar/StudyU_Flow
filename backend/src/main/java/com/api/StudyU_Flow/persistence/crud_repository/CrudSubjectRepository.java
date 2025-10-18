package com.api.StudyU_Flow.persistence.crud_repository;
import com.api.StudyU_Flow.persistence.entity.SubjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrudSubjectRepository extends CrudRepository<SubjectEntity, Long> {
    List<SubjectEntity> findSubjectEntitiesByStudentDegree_StudentUsernameAndStudentDegreeIdStudentDegree(String username, Long idDegree);

    List<SubjectEntity> findAllByStudentDegree_IdStudentDegree(Long idStudentDegree);
    List<SubjectEntity> findAllByStudentDegree_IdStudentDegreeAndSemester(Long idStudentDegree, int nSemester);

    SubjectEntity findByIdSubject(Long idSubject);

    ResponseEntity<Void> deleteByIdSubject(Long idSubject);
}
