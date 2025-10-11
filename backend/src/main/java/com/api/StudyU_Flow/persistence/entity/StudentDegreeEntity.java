package com.api.StudyU_Flow.persistence.entity;

import com.api.StudyU_Flow.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "student_degrees")
@EntityListeners(AuditingEntityListener.class)
public class StudentDegreeEntity extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student_degree", nullable = false)
    private Long idStudentDegree;

    @Column(name = "university_name", nullable = false, length = 100)
    private String universityName;

    @Column(name = "total_credits", nullable = false)
    private Integer totalCredits;

    @Column(name = "quantity_semesters")
    private Integer quantitySemesters;

    @ManyToOne(targetEntity = StudentEntity.class)
    @JoinColumn(name = "id_student", referencedColumnName = "id_student")
    private StudentEntity student;

    @ManyToOne(targetEntity = DegreeEntity.class)
    @JoinColumn(name = "id_degree", referencedColumnName = "id_degree")
    private DegreeEntity degree;

    @OneToMany(mappedBy = "studentDegree", cascade = CascadeType.ALL)
    private List<SubjectEntity> subjects;

    public StudentDegreeEntity() {
    }

    public StudentDegreeEntity(Long idStudentDegree, String universityName, Integer totalCredits, Integer quantitySemesters, StudentEntity student, DegreeEntity degree) {
        this.idStudentDegree = idStudentDegree;
        this.universityName = universityName;
        this.totalCredits = totalCredits;
        this.quantitySemesters = quantitySemesters;
        this.student = student;
        this.degree = degree;
    }

    public Long getIdStudentDegree() {
        return idStudentDegree;
    }

    public void setIdStudentDegree(Long idStudentDegree) {
        this.idStudentDegree = idStudentDegree;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }

    public Integer getQuantitySemesters() {
        return quantitySemesters;
    }

    public void setQuantitySemesters(Integer quantitySemesters) {
        this.quantitySemesters = quantitySemesters;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public DegreeEntity getDegree() {
        return degree;
    }

    public void setDegree(DegreeEntity degree) {
        this.degree = degree;
    }
}
