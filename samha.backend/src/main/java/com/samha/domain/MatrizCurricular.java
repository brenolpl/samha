package com.samha.domain;

import com.samha.domain.log.MatrizCurricularAud;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Audited
@Table(name = "matriz_curricular")
public class MatrizCurricular extends BaseLogEntity implements Comparable<Object> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private int ano;
    
    @Column(nullable = false)
    private int semestre;
       
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    public MatrizCurricular() {
    }

    public MatrizCurricular(int id, String nome, int ano, int semestre, Curso curso) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.semestre = semestre;
        this.curso = curso;
    }

    public MatrizCurricular(String nome, int ano, int semestre, Curso curso) {
        this.nome = nome;
        this.ano = ano;
        this.semestre = semestre;
        this.curso = curso;
    }

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

    public int getAno() {
        return ano;
    }

    public void setAno(int anoCriacao) {
        this.ano = anoCriacao;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    @Override
    public String toString() {
        return nome;
    }
   
    public Object[] toArray() {
        return new Object[] { this };
    }

    @Override
    public int compareTo(Object o) {
        MatrizCurricular other = (MatrizCurricular) o;
        return this.getNome().compareTo(other.getNome());
    }

    @Override
    public Class getLogEntity() {
        return MatrizCurricularAud.class;
    }
}
