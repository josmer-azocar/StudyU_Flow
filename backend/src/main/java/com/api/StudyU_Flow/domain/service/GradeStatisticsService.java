package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.Enum.Status;
import com.api.StudyU_Flow.domain.dto.response.*;
import com.api.StudyU_Flow.domain.tools.MathCalculations;
import com.api.StudyU_Flow.persistence.impl_repository.StudentDegreeEntityRepository;
import com.api.StudyU_Flow.persistence.impl_repository.StudentEntityRepository;
import com.api.StudyU_Flow.persistence.impl_repository.SubjectEntityRepository;
import org.springframework.stereotype.Service;

import java.util.*;

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

        for (SubjectAndRecordResponseDto subject : subjectsAndRecords) {
            if (subject != null) {
                if (subject.recordData().status().equals(Status.PASSED)) {
                    approvedSubjects++;
                    approvedCredits = approvedCredits + subject.subjectData().credits();
                    semesters.add(subject.subjectData().semester());
                }

                if (subject.recordData().status().equals(Status.IN_PROGRESS)) {
                    semesters.add(subject.subjectData().semester());
                }
            }
        }

        missingCredits = studentDegreeDto.totalCredits() - approvedCredits;
        percentage = MathCalculations.getPercentage(approvedCredits, studentDegreeDto.totalCredits());
        currentSemester = MathCalculations.findMaximumInCollection(semesters);

        return  new DegreeCurrentProgressDto(
                MathCalculations.roundDouble(percentage, 1),
                approvedCredits,
                approvedSubjects,
                currentSemester,
                missingCredits);
    }


    public List<SubjectAndRecordResponseDto> getTopFiveBestSubjectsGrades(Long idStudentDegree) {
        StudentDegreeResponseDto studentDegreeDto = this.studentDegreeEntityRepository.getByIdStudentDegree(idStudentDegree);
        StudentResponseDto studentDto = this.studentEntityRepository.getByIdStudent(studentDegreeDto.idStudent());

        List<SubjectAndRecordResponseDto> subjectsAndRecords = this.subjectEntityRepository
                .getAllSubjectsAndRecordsByStudent(studentDto.username(), idStudentDegree);

        List<SubjectAndRecordResponseDto> approvedSubjectsAndRecords = new ArrayList<>();

        for (SubjectAndRecordResponseDto subject : subjectsAndRecords) {
            if (subject != null) {
                if (subject.recordData().status().equals(Status.PASSED)) {
                    approvedSubjectsAndRecords.add(subject);
                }
            }
        }

        Map<Long, Double> approvedSubjectsMap = new HashMap<>();
        for (SubjectAndRecordResponseDto subject : approvedSubjectsAndRecords) {
            approvedSubjectsMap.put(subject.recordData().idRecord(), subject.recordData().finalGrade());
        }

        Map<Long, Double> top5SubjectsAndRecordsMap = MathCalculations.getTop5EntriesByValue(approvedSubjectsMap);

        Set<Long> idRecords = top5SubjectsAndRecordsMap.keySet();
        List<SubjectAndRecordResponseDto> top5SubjectsAndRecordsList = new ArrayList<>();

        for (Long idRecord : idRecords) {
            for (SubjectAndRecordResponseDto subject : approvedSubjectsAndRecords) {
                if (idRecord != null && subject != null) {
                    if (subject.recordData().idRecord() == idRecord) {
                        top5SubjectsAndRecordsList.add(subject);
                    }
                }
            }
        }

        return top5SubjectsAndRecordsList;
    }

    public GeneralAndWeightedAverageDto getGeneralAndWeightedAverage(Long idStudentDegree) {
        StudentDegreeResponseDto studentDegreeDto = this.studentDegreeEntityRepository.getByIdStudentDegree(idStudentDegree);
        StudentResponseDto studentDto = this.studentEntityRepository.getByIdStudent(studentDegreeDto.idStudent());

        List<SubjectAndRecordResponseDto> subjectsAndRecords = this.subjectEntityRepository
                .getAllSubjectsAndRecordsByStudent(studentDto.username(), idStudentDegree);

        Double generalAverage = 0.0;
        Double weightedAverage = 0.0;
        Double cumulativeGrade = 0.0;
        Integer cumulativeCredits = 0;
        Double cumulativeGradeXcredits = 0.0;
        Integer numSubjects = 0;

        for (SubjectAndRecordResponseDto subject : subjectsAndRecords) {
            if (subject != null) {
                if (subject.recordData().status().equals(Status.PASSED) || subject.recordData().status().equals(Status.FAILED)) {
                    //General Average
                    cumulativeGrade = cumulativeGrade + subject.recordData().finalGrade();
                    numSubjects++;

                    //Weighted Average
                    Double gradeXcredits = 0.0;
                    gradeXcredits = subject.recordData().finalGrade() * subject.subjectData().credits();
                    cumulativeGradeXcredits = cumulativeGradeXcredits + gradeXcredits;
                    cumulativeCredits = cumulativeCredits + subject.subjectData().credits();
                }
            }
        }

        generalAverage = cumulativeGrade/numSubjects;
        weightedAverage = cumulativeGradeXcredits/cumulativeCredits;

        return new GeneralAndWeightedAverageDto(
                MathCalculations.roundDouble(generalAverage, 2),
                MathCalculations.roundDouble(weightedAverage, 2)
        );
    }
}
