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

    @ManyToOne(targetEntity = DegreeEntity.class)
    @JoinColumn(name = "id_degree", referencedColumnName = "id_degree")
    private DegreeEntity degree;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<StudentSubjectRecordEntity> records;

    public SubjectEntity() {
    }

    public SubjectEntity(Long idSubject, String name, Integer credits, Integer semester) {
        this.idSubject = idSubject;
        this.name = name;
        this.credits = credits;
        this.semester = semester;
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
