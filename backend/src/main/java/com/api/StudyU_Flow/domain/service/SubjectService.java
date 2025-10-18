package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.dto.request.StudentSubjectRecordRequestDto;
import com.api.StudyU_Flow.domain.dto.request.SubjectAndRecordRequestDto;
import com.api.StudyU_Flow.domain.dto.request.SubjectRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentSubjectRecordResponseDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectAndRecordResponseDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectResponseDto;
import com.api.StudyU_Flow.persistence.impl_repository.SubjectEntityRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    public SubjectAndRecordResponseDto add(SubjectAndRecordRequestDto requestDto) {
        return this.subjectEntityRepository.add(requestDto);
    }

    public List<SubjectResponseDto> getAllByStudent(String username, Long idDegree) {
        return this.subjectEntityRepository.getAllByStudent(username, idDegree);
    }

    public List<SubjectAndRecordResponseDto> getAllSubjectsAndRecordsByStudent(String username, Long idStudentDegree) {
        return this.subjectEntityRepository.getAllSubjectsAndRecordsByStudent(username, idStudentDegree);
    }

    public List<SubjectAndRecordResponseDto> getAllSubjectsAndRecordsBySemester(String username, Long idStudentDegree, Integer nSemester) {
        return this.subjectEntityRepository.getAllSubjectsAndRecordsBySemester(username, idStudentDegree, nSemester);
    }

    public StudentSubjectRecordResponseDto addRecordToSubject(String username, Long idSubject, StudentSubjectRecordRequestDto requestDto) {
        return this.subjectEntityRepository.addRecordToSubject(username, idSubject, requestDto);
    }

    public StudentSubjectRecordResponseDto updateRecord(Long idRecord, StudentSubjectRecordRequestDto requestDto) {
        return this.subjectEntityRepository.updateRecord(idRecord, requestDto);
    }

    public ResponseEntity<Void> deleteSubjectAndRecordByIdSubject(Long idSubject) {
        return this.subjectEntityRepository.deleteSubjectAndRecordByIdSubject(idSubject);
    }

    public SubjectAndRecordResponseDto getSubjectAndRecordByIdSubject(Long idSubject) {
        return this.subjectEntityRepository.getSubjectAndRecordByIdSubject(idSubject);
    }
}
