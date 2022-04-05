package com.brenoleal.samha.persistence;

import java.util.List;

import com.brenoleal.samha.core.Turma;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class GdTurma extends GdGenerico{
    
    private GdPrincipal gdPrincipal;
    
    public GdTurma(GdPrincipal gdPrincipal){
        this.gdPrincipal = gdPrincipal;
    }
    
    public List filtrarPorCurso(int id) {
        Criteria crit = criarSessao().createCriteria(Turma.class);
        sessao.beginTransaction();
        crit.createAlias("matriz", "m");
        crit.createAlias("m.curso", "c");
        crit.add( Restrictions.eq("c.id", id) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List filtrarPorEixo(int id) {
        Criteria crit = criarSessao().createCriteria(Turma.class);
        sessao.beginTransaction();
        crit.createAlias("matriz", "m");
        crit.createAlias("m.curso", "c");
        crit.createAlias("c.coordenadoria", "co");
        crit.setFetchMode("co", FetchMode.JOIN);
        crit.createAlias("co.eixo", "e");
        crit.add( Restrictions.eq("e.id", id) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List buscar(String coluna, String texto) {
        Criteria crit = criarSessao().createCriteria(Turma.class);
        sessao.beginTransaction();
        crit.add( Restrictions.like(coluna, texto, MatchMode.ANYWHERE) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
}
