package com.api.StudyU_Flow.persistence.entity;

import com.api.StudyU_Flow.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "student_subject_records")
@EntityListeners(AuditingEntityListener.class)
public class StudentSubjectRecordEntity extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_record", nullable = false)
    private Long idRecord;;

    @Column(name = "status", length = 10, nullable = false)
    private String status;

    @Column(name = "final_grade")
    private double finalGrade;

    @Column(name = "attempts")
    private Integer attempts;

    @ManyToOne(targetEntity = StudentEntity.class)
    @JoinColumn(name = "id_student", referencedColumnName = "id_student")
    private StudentEntity student;

    @ManyToOne(targetEntity = SubjectEntity.class)
    @JoinColumn(name = "id_subject", referencedColumnName = "id_subject")
    private SubjectEntity subject;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private List<EvaluationEntity> evaluations;

    public StudentSubjectRecordEntity() {
    }

    public StudentSubjectRecordEntity(Long idRecord, String status, double finalGrade, Integer attempts, StudentEntity student, SubjectEntity subject) {
        this.idRecord = idRecord;
        this.status = status;
        this.finalGrade = finalGrade;
        this.attempts = attempts;
        this.student = student;
        this.subject = subject;
    }

    public Long getIdRecord() {
        return idRecord;
    }

    public void setIdRecord(Long idRecord) {
        this.idRecord = idRecord;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }
}
