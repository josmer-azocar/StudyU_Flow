package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.dto.response.DegreeCurrentProgressDto;
import com.api.StudyU_Flow.domain.dto.response.StudentDegreeResponseDto;
import com.api.StudyU_Flow.domain.dto.response.StudentResponseDto;
import com.api.StudyU_Flow.domain.dto.response.SubjectAndRecordResponseDto;
import com.api.StudyU_Flow.domain.tools.MathCalculations;
import com.api.StudyU_Flow.persistence.impl_repository.StudentDegreeEntityRepository;
import com.api.StudyU_Flow.persistence.impl_repository.StudentEntityRepository;
import com.api.StudyU_Flow.persistence.impl_repository.SubjectEntityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeStatisticsService {
    private final StudentDegreeEntityRepository studentDegreeEntityRepository;
    private final StudentEntityRepository studentEntityRepository;
    private final SubjectEntityRepository subjectEntityRepository;

    public GradeStatisticsService(StudentDegreeEntityRepository studentDegreeEntityRepository, StudentEntityRepository studentEntityRepository, SubjectEntityRepository subjectEntityRepository) {
        this.studentDegreeEntityRepository = studentDegreeEntityRepository;
        this.studentEntityRepository = studentEntityRepository;
        this.subjectEntityRepository = subjectEntityRepository;
    }

    public DegreeCurrentProgressDto getDegreeCurrentProgress(Long idStudentDegree) {
        StudentDegreeResponseDto studentDegreeDto = this.studentDegreeEntityRepository.getByIdStudentDegree(idStudentDegree);
        StudentResponseDto studentDto = this.studentEntityRepository.getByIdStudent(studentDegreeDto.idStudent());

        List<SubjectAndRecordResponseDto> subjectsAndRecords = this.subjectEntityRepository
                .getAllSubjectsAndRecordsByStudent(studentDto.username(), idStudentDegree);

        Double percentage = 0.0;
        Integer approvedSubjects = 0;
        Integer approvedCredits = 0;
        Integer missingCredits = 0;
        Integer currentSemester = 0;
        //Integer missingSubjects = 0;

        List<Integer> semesters = new ArrayList<>();

        for(SubjectAndRecordResponseDto subject : subjectsAndRecords){
            if(subject != null){
                if(subject.recordData().status().equals("PASSED")){
                    approvedSubjects++;
                    approvedCredits = approvedCredits + subject.subjectData().credits();
                }

                if(subject.recordData().status().equals("IN_PROGRESS")){
                    semesters.add(subject.subjectData().semester());
                }
            }
        }

        missingCredits = studentDegreeDto.totalCredits() - approvedCredits;
        percentage = MathCalculations.getPercentage(approvedCredits, studentDegreeDto.totalCredits());
        currentSemester = MathCalculations.findMaximumInCollection(semesters);

        DegreeCurrentProgressDto degreeCurrentProgressDto = new DegreeCurrentProgressDto(
                percentage, approvedCredits, approvedSubjects, currentSemester, missingCredits);

        return degreeCurrentProgressDto;
    }


}
