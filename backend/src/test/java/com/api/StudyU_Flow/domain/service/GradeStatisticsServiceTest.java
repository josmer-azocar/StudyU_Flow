package com.api.StudyU_Flow.domain.service;

import com.api.StudyU_Flow.domain.Enum.Gender;
import com.api.StudyU_Flow.domain.Enum.Status;
import com.api.StudyU_Flow.domain.dto.response.*;
import com.api.StudyU_Flow.persistence.impl_repository.StudentDegreeEntityRepository;
import com.api.StudyU_Flow.persistence.impl_repository.StudentEntityRepository;
import com.api.StudyU_Flow.persistence.impl_repository.SubjectEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GradeStatisticsServiceTest {

    @Mock
    private StudentDegreeEntityRepository studentDegreeEntityRepository;
    @Mock
    private StudentEntityRepository studentEntityRepository;
    @Mock
    private SubjectEntityRepository subjectEntityRepository;

    @InjectMocks
    private GradeStatisticsService gradeStatisticsService;


    public StudentResponseDto STUDENT_RESPONSE = new StudentResponseDto(
            1L,
            "Pepe12",
            "Pepe",
            "Del Mar",
            null,
            Gender.MALE);


    public StudentDegreeResponseDto STUDENT_DEGREE_DTO = new StudentDegreeResponseDto(
            1L,
            1L,
            1L,
            8,
            100,
            "uneg"
    );


    @Test
    void getDegreeCurrentProgress() {
        DegreeCurrentProgressDto degreeCurrentProgressDto = new DegreeCurrentProgressDto(
                4.0,
                4,
                1,
                2,
                96
        );

        List<SubjectAndRecordResponseDto> list = new ArrayList<>(List.of(
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(1L, "Math", 4, 1, 1L),
                        new StudentSubjectRecordResponseDto(100L, 1, 4.5, Status.PASSED, 1L, 1L)
                ),
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(2L, "History", 3, 2, 1L),
                        new StudentSubjectRecordResponseDto(101L, 2, 3.8, Status.IN_PROGRESS, 1L, 2L)
                )
        ));

       when(studentDegreeEntityRepository.getByIdStudentDegree(1L)).thenReturn(STUDENT_DEGREE_DTO);
       when(studentEntityRepository.getByIdStudent(1L)).thenReturn(STUDENT_RESPONSE);
       when(subjectEntityRepository.getAllSubjectsAndRecordsByStudent("Pepe12", 1L)).thenReturn(list);

       DegreeCurrentProgressDto result = gradeStatisticsService.getDegreeCurrentProgress(1L);

        assertEquals(degreeCurrentProgressDto, result);
    }

    @Test
    void getTopFiveBestSubjectsGrades() {

        List<SubjectAndRecordResponseDto> originaList = new ArrayList<>(List.of(
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(1L, "Math", 4, 1, 1L),
                        new StudentSubjectRecordResponseDto(100L, 1, 3.0, Status.PASSED, 1L, 1L)
                ),
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(2L, "History", 3, 2, 1L),
                        new StudentSubjectRecordResponseDto(101L, 2, 3.5, Status.IN_PROGRESS, 1L, 2L)
                ),
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(3L, "physics", 4, 1, 1L),
                        new StudentSubjectRecordResponseDto(102L, 3, 4.5, Status.PASSED, 1L, 3L)
                ),
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(4L, "chemistry", 4, 1, 1L),
                        new StudentSubjectRecordResponseDto(103L, 4, 5.0, Status.PASSED, 1L, 4L)
                ),
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(5L, "Biology", 3, 2, 1L),
                        new StudentSubjectRecordResponseDto(104L, 5, 5.5, Status.PASSED, 1L, 5L)
                ),
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(6L, "Spanish", 2, 2, 1L),
                        new StudentSubjectRecordResponseDto(105L, 6, 6.0, Status.PASSED, 1L, 6L)
                ),
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(7L, "English", 2, 2, 1L),
                        new StudentSubjectRecordResponseDto(106L, 7, 7.0, Status.IN_PROGRESS, 1L, 7L)
                ),
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(8L, "Art", 1, 3, 1L),
                        new StudentSubjectRecordResponseDto(107L, 8, 8.0, Status.PASSED, 1L, 8L)
                )
        ));

        List<SubjectAndRecordResponseDto> expectedList = new ArrayList<>(
                List.of(
                        originaList.get(7),
                        originaList.get(5),
                        originaList.get(4),
                        originaList.get(3),
                        originaList.get(2)
                ));

        when(studentDegreeEntityRepository.getByIdStudentDegree(1L)).thenReturn(STUDENT_DEGREE_DTO);
        when(studentEntityRepository.getByIdStudent(1L)).thenReturn(STUDENT_RESPONSE);
        when(subjectEntityRepository.getAllSubjectsAndRecordsByStudent("Pepe12", 1L)).thenReturn(originaList);

        List<SubjectAndRecordResponseDto> result = gradeStatisticsService.getTopFiveBestSubjectsGrades(1L);

        assertEquals(expectedList, result);
    }

    @Test
    void getGeneralAndWeightedAverage() {
        List<SubjectAndRecordResponseDto> originaList = new ArrayList<>(List.of(
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(5L, "Biology", 3, 2, 1L),
                        new StudentSubjectRecordResponseDto(104L, 5, 5.5, Status.PASSED, 1L, 5L)
                ),
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(6L, "Spanish", 2, 2, 1L),
                        new StudentSubjectRecordResponseDto(105L, 6, 6.0, Status.PASSED, 1L, 6L)
                ),
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(7L, "English", 2, 2, 1L),
                        new StudentSubjectRecordResponseDto(106L, 7, 7.0, Status.IN_PROGRESS, 1L, 7L)
                ),
                new SubjectAndRecordResponseDto(
                        new SubjectResponseDto(8L, "Art", 1, 3, 1L),
                        new StudentSubjectRecordResponseDto(107L, 8, 8.0, Status.PASSED, 1L, 8L)
                )
        ));

        when(studentDegreeEntityRepository.getByIdStudentDegree(1L)).thenReturn(STUDENT_DEGREE_DTO);
        when(studentEntityRepository.getByIdStudent(1L)).thenReturn(STUDENT_RESPONSE);
        when(subjectEntityRepository.getAllSubjectsAndRecordsByStudent("Pepe12", 1L)).thenReturn(originaList);

        GeneralAndWeightedAverageDto result = gradeStatisticsService.getGeneralAndWeightedAverage(1L);

        GeneralAndWeightedAverageDto expectedResult = new GeneralAndWeightedAverageDto(
                6.5,
                6.08
                );

        assertEquals(expectedResult, result);
    }
}