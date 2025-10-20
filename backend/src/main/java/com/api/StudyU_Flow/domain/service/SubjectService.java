package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.dto.request.StudentSubjectRecordRequestDto;
import com.api.StudyU_Flow.domain.dto.request.SubjectAndRecordRequestDto;
import com.api.StudyU_Flow.domain.dto.request.SubjectRequestDto;
import com.api.StudyU_Flow.domain.dto.response.StudentSubjectRecordResponseDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectAndRecordResponseDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectResponseDto;
import com.api.StudyU_Flow.persistence.impl_repository.SubjectEntityRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
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

    @Tool(description = "Obtains a list of subjects with their information according to the username and idStudentDegree")
    public List<SubjectAndRecordResponseDto> getAllSubjectsAndRecordsByStudent(
            @ToolParam(description = "username") String username,
            @ToolParam(description = "idStudentDegree") Long idStudentDegree) {
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

    public List<SubjectResponseDto> addSubjectList(List<SubjectRequestDto> requestDto) {
        return this.subjectEntityRepository.addSubjectList(requestDto);
    }

    public List<SubjectAndRecordResponseDto> addSubjectAndRecordList(List<SubjectAndRecordRequestDto> requestDto) {
        return this.subjectEntityRepository.addSubjectAndRecordList(requestDto);
    }
}
