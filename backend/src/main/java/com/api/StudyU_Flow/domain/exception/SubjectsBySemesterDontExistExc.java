package com.api.StudyU_Flow.domain.exception;

public class SubjectsBySemesterDontExistExc extends RuntimeException {
    public SubjectsBySemesterDontExistExc(Long degreeId, Integer semester) {
        super("there are not any subjects by Student Degree Id: "+ degreeId + " and semester n°: "+ semester);
    }
}
