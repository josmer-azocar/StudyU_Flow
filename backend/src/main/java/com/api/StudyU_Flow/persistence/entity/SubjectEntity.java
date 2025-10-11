package com.api.StudyU_Flow.persistence.entity;

import com.api.StudyU_Flow.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "subjects")
@EntityListeners(AuditingEntityListener.class)
public class SubjectEntity  extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subject", nullable = false)
    private Long idSubject;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "semester")
    private Integer semester;

    @ManyToOne(targetEntity = StudentDegreeEntity.class)
    @JoinColumn(name = "id_student_degree", referencedColumnName = "id_student_degree")
    private StudentDegreeEntity studentDegree;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<StudentSubjectRecordEntity> records;

    public SubjectEntity() {
    }

    public SubjectEntity(Long idSubject, Integer credits, String name, Integer semester, StudentDegreeEntity studentDegree) {
        this.idSubject = idSubject;
        this.credits = credits;
        this.name = name;
        this.semester = semester;
        this.studentDegree = studentDegree;
    }

    public StudentDegreeEntity getStudentDegree() {
        return studentDegree;
    }

    public void setStudentDegree(StudentDegreeEntity studentDegree) {
        this.studentDegree = studentDegree;
    }

    public Long getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(Long idSubject) {
        this.idSubject = idSubject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }
}
