package com.brenoleal.persistence.legacy;

import com.brenoleal.core.Usuario;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class GdPrincipal extends GdGenerico{
    
    private GdAlocacao gdAlocacao;
    private GdAula gdAula;
    private GdCoordenador gdCoordenador;
    private GdCoordenadoria gdCoordenadoria;
    private GdCurso gdCurso;
    private GdDisciplina gdDisciplina;
    private GdEixo gdEixo;
    private GdMatriz gdMatriz;
    private GdOferta gdOferta;
    private GdProfessor gdProfessor;
    private GdRestricao gdRestricao;
    private GdTurma gdTurma;

    public GdPrincipal() {
        gdAlocacao = new GdAlocacao(this);
        gdAula = new GdAula(this);
        gdCoordenador = new GdCoordenador(this);
        gdCoordenadoria = new GdCoordenadoria(this);
        gdCurso = new GdCurso(this);
        gdDisciplina = new GdDisciplina(this);
        gdEixo = new GdEixo(this);
        gdMatriz = new GdMatriz(this);
        gdOferta = new GdOferta(this);
        gdProfessor = new GdProfessor(this);
        gdRestricao = new GdRestricao(this);
        gdTurma = new GdTurma(this);   
    }
    
    public Usuario validarAcesso(String login, String senha){
        Criteria crit = criarSessao().createCriteria(Usuario.class);
        sessao.beginTransaction();
        crit.add(Restrictions.eq("login", login));
        crit.add(Restrictions.eq("senha", senha));
        crit.setMaxResults(1);
        Usuario usuario = (Usuario) crit.uniqueResult();
        sessao.getTransaction().commit();
        sessao.close();
        return usuario;   
    }

    public GdAlocacao getGdAlocacao() {
        return gdAlocacao;
    }

    public GdAula getGdAula() {
        return gdAula;
    }

    public GdCoordenador getGdCoordenador() {
        return gdCoordenador;
    }

    public GdCoordenadoria getGdCoordenadoria() {
        return gdCoordenadoria;
    }

    public GdCurso getGdCurso() {
        return gdCurso;
    }

    public GdDisciplina getGdDisciplina() {
        return gdDisciplina;
    }

    public GdEixo getGdEixo() {
        return gdEixo;
    }

    public GdMatriz getGdMatriz() {
        return gdMatriz;
    }

    public GdOferta getGdOferta() {
        return gdOferta;
    }

    public GdProfessor getGdProfessor() {
        return gdProfessor;
    }

    public GdRestricao getGdRestricao() {
        return gdRestricao;
    }

    public GdTurma getGdTurma() {
        return gdTurma;
    }  
}
