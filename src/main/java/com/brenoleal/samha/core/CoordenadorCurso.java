package com.brenoleal.samha.core;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "coordenador_curso")
@PrimaryKeyJoinColumn(name = "coordenador_curso_id")
public class CoordenadorCurso extends Usuario{
             
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id", nullable = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Professor professor;

    public CoordenadorCurso() {
    }

    public CoordenadorCurso(Professor professor, int id, String login, String senha) {
        super(id, login, senha);
        this.professor = professor;
    }

    public CoordenadorCurso(Professor professor, String login, String senha) {
        super(login, senha);
        this.professor = professor;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        return professor.getNome();
    }

    public Object[] toArray() {
        return new Object[] { this, getProfessor().getMatricula(), "COORDENADOR DE CURSO"};
    }
}
