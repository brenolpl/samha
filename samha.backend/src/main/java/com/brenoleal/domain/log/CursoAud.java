package com.brenoleal.domain.log;

import com.brenoleal.domain.BaseLogEntity;
import com.brenoleal.domain.Coordenadoria;
import com.brenoleal.domain.Professor;

import javax.persistence.*;

@Entity
@Table(name = "curso_aud")
public class CursoAud extends BaseLogEntity {

    @EmbeddedId
    private AuditCompositeKey pk;

    @Column(name = "revtype", updatable = false)
    private Integer revtype;

    @Column(name = "nome", updatable = false)
    private String nome;

    @Column(name = "qt_periodos", updatable = false)
    private Integer qtPeriodos;

    @Column(name = "nivel", updatable = false)
    private String nivel;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coordenadoria_id", updatable = false)
    private Coordenadoria coordenadoria;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id", updatable = false)
    private Professor professor;

    public AuditCompositeKey getPk() {
        return pk;
    }

    public void setPk(AuditCompositeKey pk) {
        this.pk = pk;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtPeriodos() {
        return qtPeriodos;
    }

    public void setQtPeriodos(Integer qtPeriodos) {
        this.qtPeriodos = qtPeriodos;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Coordenadoria getCoordenadoria() {
        return coordenadoria;
    }

    public void setCoordenadoria(Coordenadoria coordenadoria) {
        this.coordenadoria = coordenadoria;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Integer getRevtype() {
        return revtype;
    }

    public void setRevtype(Integer revtype) {
        this.revtype = revtype;
    }
}
