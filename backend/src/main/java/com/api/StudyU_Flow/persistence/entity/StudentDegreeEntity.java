package com.api.StudyU_Flow.persistence.entity;

import com.api.StudyU_Flow.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Data
@Entity
@Table(name = "student_degrees")
@EntityListeners(AuditingEntityListener.class)
public class StudentDegreeEntity extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student_degree", nullable = false)
    private Long idStudentDegree;

    @ManyToOne(targetEntity = StudentEntity.class)
    @JoinColumn(name = "id_student", referencedColumnName = "id_student", insertable = false, updatable = false)
    private StudentEntity student;

    @ManyToOne(targetEntity = DegreeEntity.class)
    @JoinColumn(name = "id_degree", referencedColumnName = "id_degree", insertable = false, updatable = false)
    private DegreeEntity degree;

    public StudentDegreeEntity() {
    }

    public StudentDegreeEntity(Long idStudentDegree) {
        this.idStudentDegree = idStudentDegree;
    }

    public Long getIdStudentDegree() {
        return idStudentDegree;
    }

    public void setIdStudentDegree(Long idStudentDegree) {
        this.idStudentDegree = idStudentDegree;
    }

}
