package com.brenoleal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "turma")
public class Turma extends BaseLogEntity implements Comparable<Object> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false, unique = true)
    private String nome;
    
    @Column(nullable = false)
    private String turno;
    
    @Column(nullable = false)
    private int ano;
    
    @Column(nullable = false)
    private int semestre;

    @Column(name = "ativa", nullable = false)
    private Boolean ativa;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matriz_curricular_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @Cascade(CascadeType.SAVE_UPDATE)
    private MatrizCurricular matriz;
    
    public Turma() {
    }

    public Turma(int id, String nome, int ano, int semestre, String turno, MatrizCurricular matriz) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.semestre = semestre;
        this.matriz = matriz;
        this.turno = turno;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public Turma(String nome, int ano, int semestre, String turno, MatrizCurricular matriz) {
        this.nome = nome;
        this.ano = ano;
        this.semestre = semestre;
        this.matriz = matriz;
        this.turno = turno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }
    
    public MatrizCurricular getMatriz() {
        return matriz;
    }

    public void setMatriz(MatrizCurricular matriz) {
        this.matriz = matriz;
    }
    
    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
   
    @Override
    public String toString() {
        return nome;
    }
    
    public Object[] toArray() {
        return new Object[] { this, getAnoSemestre(), getMatriz().getNome(), getMatriz().getCurso().getNome(), getTurno() };
    }
    
    public String getAnoSemestre(){
        return String.valueOf(getAno()) + "/" + String.valueOf(getSemestre());
    }

    @Override
    public int compareTo(Object o) {
        
        Turma other = (Turma) o;
        return this.getNome().compareTo(other.getNome());
        
    }
}
