package com.brenoleal.samha.core;

import java.io.Serializable;
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
@Table(name = "oferta")
public class Oferta implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private int ano;
    
    @Column(nullable = false)
    private int semestre;
    
    @Column(nullable = false)
    private int tempoMaximoTrabalho;
    
    @Column(nullable = false)
    private int intervaloMinimo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    public Oferta() {
    }

    public Oferta(int id, int ano, int semestre, int tempoMaximoTrabalho, int intervaloMinimo, Turma turma) {
        this.id = id;
        this.semestre = semestre;
        this.tempoMaximoTrabalho = tempoMaximoTrabalho;
        this.intervaloMinimo = intervaloMinimo;
        this.turma = turma;
    }

    public Oferta(int ano, int semestre, int tempoMaximoTrabalho, int intervaloMinimo, Turma turma) {
        this.semestre = semestre;
        this.tempoMaximoTrabalho = tempoMaximoTrabalho;
        this.intervaloMinimo = intervaloMinimo;
        this.turma = turma;
    }

    public double getTempoMaximoTrabalho() {
        return tempoMaximoTrabalho;
    }

    public void setTempoMaximoTrabalho(int tempoMaximoTrabalho) {
        this.tempoMaximoTrabalho = tempoMaximoTrabalho;
    }

    public double getIntervaloMinimo() {
        return intervaloMinimo;
    }

    public void setIntervaloMinimo(int intervaloMinimo) {
        this.intervaloMinimo = intervaloMinimo;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    } 
}
