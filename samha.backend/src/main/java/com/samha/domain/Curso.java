package com.samha.domain;

import com.samha.domain.log.CursoAud;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Audited
@Table(name = "curso")
public class Curso extends BaseLogEntity implements Comparable<Object>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String nome;
    
    @Column(nullable = false)
    private Integer qtPeriodos;
    
    @Column(nullable = false)
    private String nivel;

    @Column(nullable = false)
    private Boolean semestral;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coordenadoria_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Coordenadoria coordenadoria;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Professor professor;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtPeriodos() {
        return qtPeriodos;
    }

    public void setQtPeriodos(int qtPeriodos) {
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
    
    @Override
    public String toString() {
        return nome;
    }
   
    public Object[] toArray() {
        return new Object[] { this, getNivel()};
    }

    @Override
    public int compareTo(Object o) {
        Curso other = (Curso) o;
        return this.getNome().compareTo(other.getNome());
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Override
    public Class getLogEntity() {
        return CursoAud.class;
    }

    public void setQtPeriodos(Integer qtPeriodos) {
        this.qtPeriodos = qtPeriodos;
    }

    public Boolean getSemestral() {
        return semestral;
    }

    public void setSemestral(Boolean semestral) {
        this.semestral = semestral;
    }
}
