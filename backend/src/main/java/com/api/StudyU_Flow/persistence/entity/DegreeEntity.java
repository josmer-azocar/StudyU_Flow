package com.api.StudyU_Flow.persistence.entity;

import com.api.StudyU_Flow.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "degrees")
@EntityListeners(AuditingEntityListener.class)
public class DegreeEntity extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_degree", nullable = false)
    private Long idDegree;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "university_name", nullable = false, length = 100)
    private String universityName;

    @Column(name = "total_credits", nullable = false)
    private Integer totalCredits;

    @Column(name = "quantity_semesters")
    private Integer quantitySemesters;

    @OneToMany(mappedBy = "degree", cascade = CascadeType.ALL)
    private List<StudentDegreeEntity> studentDegrees;

    @OneToMany(mappedBy = "degree", cascade = CascadeType.ALL)
    private List<SubjectEntity> subjects;

    public DegreeEntity() {
    }

    public DegreeEntity(Long idDegree, String name, String universityName, Integer totalCredits, Integer quantitySemesters) {
        this.idDegree = idDegree;
        this.name = name;
        this.universityName = universityName;
        this.totalCredits = totalCredits;
        this.quantitySemesters = quantitySemesters;
    }

    public Long getIdDegree() {
        return idDegree;
    }

    public void setIdDegree(Long idDegree) {
        this.idDegree = idDegree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}

