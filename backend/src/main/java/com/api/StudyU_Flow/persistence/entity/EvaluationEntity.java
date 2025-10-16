package com.api.StudyU_Flow.persistence.entity;

import com.api.StudyU_Flow.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;


@Data
@Entity
@Table(name = "evaluations")
@EntityListeners(AuditingEntityListener.class)
public class EvaluationEntity extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluation", nullable = false)
    private Long idEvaluation;

    @Column(name = "evaluation_name", nullable = false, length = 100)
    private String evaluationName;

    @Column(name = "grade")
    private double grade;

    @Column(name = "max_grade", nullable = false)
    private double maxGrade;

    @Column(name = "percentage", nullable = false)
    private double percentage;

    @ManyToOne(targetEntity = StudentSubjectRecordEntity.class)
    @JoinColumn(name = "id_record", referencedColumnName = "id_record")
    private StudentSubjectRecordEntity record;

    public EvaluationEntity() {
    }

    public EvaluationEntity(Long idEvaluation, String evaluationName, double grade, double maxGrade, double percentage, StudentSubjectRecordEntity record) {
        this.idEvaluation = idEvaluation;
        this.evaluationName = evaluationName;
        this.grade = grade;
        this.maxGrade = maxGrade;
        this.percentage = percentage;
        this.record = record;
    }

    public Long getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(Long idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public String getEvaluationName() {
        return evaluationName;
    }

    public void setEvaluationName(String evaluationName) {
        this.evaluationName = evaluationName;
    }


    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(double maxGrade) {
        this.maxGrade = maxGrade;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public StudentSubjectRecordEntity getRecord() {
        return record;
    }

    public void setRecord(StudentSubjectRecordEntity record) {
        this.record = record;
    }
}
