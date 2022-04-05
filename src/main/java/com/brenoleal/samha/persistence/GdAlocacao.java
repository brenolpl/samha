package com.brenoleal.samha.persistence;

import java.util.List;

import com.brenoleal.samha.core.Alocacao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class GdAlocacao extends GdGenerico{
    
    private GdPrincipal gdPrincipal;

    public GdAlocacao(GdPrincipal gdPrincipal) {
        this.gdPrincipal = gdPrincipal;
    }

    public List filtrarPorAnoSemestreMatriz(int ano, int semestre, int periodo, int matriz){
        Criteria crit = criarSessao().createCriteria(Alocacao.class);
        sessao.beginTransaction();
        crit.createAlias("disciplina", "d");
        crit.createAlias("d.matriz", "m");
        crit.add( Restrictions.eq("d.periodo", periodo));
        crit.add( Restrictions.eq("m.id", matriz));
        crit.add( Restrictions.eq("ano", ano));
        crit.add( Restrictions.eq("semestre", semestre));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List filtrarPorAnoSemestre(int ano, int semestre){
        Criteria crit = criarSessao().createCriteria(Alocacao.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq("ano", ano));
        crit.add( Restrictions.eq("semestre", semestre));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List filtrarPorAnoSemestreProfessor(int ano, int semestre, int idProfessor){
        Criteria crit = criarSessao().createCriteria(Alocacao.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq("ano", ano));
        crit.add( Restrictions.eq("semestre", semestre));
        crit.add(Restrictions.or(Restrictions.eq("professor1.id", idProfessor), Restrictions.eq("professor2.id", idProfessor)));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public Alocacao filtrarUltimaAlocacao() {
        Criteria crit = criarSessao().createCriteria(Alocacao.class);
        crit.addOrder(Order.desc("ano"));
        crit.addOrder(Order.desc("semestre"));
        crit.setMaxResults(1);
        Alocacao alocacao = (Alocacao) crit.uniqueResult();
        sessao.close();
        return alocacao;
    }
    
    public List filtrarPorDisciplina(int id){
        Criteria crit = criarSessao().createCriteria(Alocacao.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq("disciplina.id", id));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List filtrarPorProfessor(int id){
        Criteria crit = criarSessao().createCriteria(Alocacao.class);
        sessao.beginTransaction();
        crit.add(Restrictions.or(Restrictions.eq("professor1.id", id), Restrictions.eq("professor2.id", id)));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
}
