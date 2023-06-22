package com.samha.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.samha.domain.log.AlocacaoAud;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
@Table(name = "alocacao")
public class Alocacao extends BaseLogEntity implements Comparable<Object>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private int ano;
    
    @Column(nullable = false)
    private int semestre;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disciplina_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Disciplina disciplina;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor1_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Professor professor1;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor2_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Professor professor2;

    @OneToMany(mappedBy = "alocacao")
    @JsonIgnore
    private List<Aula> aulas = new ArrayList<>();

    @Transient
    private boolean completa;

    @Transient
    private String encurtadoProfessor1;

    @Transient
    private String encurtadoProfessor2;

    @Transient
    private Turma turma;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Professor getProfessor1() {
        return professor1;
    }

    public void setProfessor1(Professor professor1) {
        this.professor1 = professor1;
    }

    public Professor getProfessor2() {
        return professor2;
    }

    public void setProfessor2(Professor professor2) {
        this.professor2 = professor2;
    }

    public boolean isCompleta() {
        return completa;
    }

    public void setCompleta(boolean completa) {
        this.completa = completa;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public Object[] toArray() {
        return new Object[] { this, disciplina.getPeriodo(), isCompleta() };
    }
    
    public Object[] toArrayAlocacao() {
        return new Object[] { getDisciplina().getNome(), getTurma().getNome(), getDisciplina().getSigla(), 
            getDisciplina().getPeriodo(), getDisciplina().getMatriz().getCurso().getNome(), getDisciplina().getQtAulas(), 
            getDisciplina().getCargaHoraria(), isCompleta() };
    }

    @Override
    public int compareTo(Object o) {
        Alocacao other = (Alocacao) o;  
        return this.getDisciplina().getSigla().compareTo(other.getDisciplina().getSigla());
    }

    public String getEncurtadoProfessor1() {
        return encurtadoProfessor1;
    }

    public void setEncurtadoProfessor1(String encurtadoProfessor1) {
        this.encurtadoProfessor1 = encurtadoProfessor1;
    }

    public String getEncurtadoProfessor2() {
        return encurtadoProfessor2;
    }

    public void setEncurtadoProfessor2(String encurtadoProfessor2) {
        this.encurtadoProfessor2 = encurtadoProfessor2;
    }

    @Override
    public Class getLogEntity() {
        return AlocacaoAud.class;
    }
}
