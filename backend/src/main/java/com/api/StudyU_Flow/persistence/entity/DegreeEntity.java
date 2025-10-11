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

    @OneToMany(mappedBy = "degree", cascade = CascadeType.ALL)
    private List<StudentDegreeEntity> studentDegrees;

    public DegreeEntity() {
    }

    public DegreeEntity(Long idDegree, String name) {
        this.idDegree = idDegree;
        this.name = name;
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

}

