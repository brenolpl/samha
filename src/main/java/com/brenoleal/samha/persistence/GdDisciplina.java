package com.brenoleal.samha.persistence;

import java.util.List;

import com.brenoleal.samha.core.Disciplina;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class GdDisciplina extends GdGenerico{
    
    private GdPrincipal gdPrincipal;
    
    public GdDisciplina(GdPrincipal gdPrincipal){
        this.gdPrincipal = gdPrincipal;
    }
    
    public List filtrarPorMatriz(String coluna, int texto) {
        Criteria crit = criarSessao().createCriteria(Disciplina.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq(coluna, texto) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List filtrarPorTipoPeriodo(String tipo, int idMatriz, int periodo) {
        Criteria crit = criarSessao().createCriteria(Disciplina.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq("matriz.id", idMatriz));
        crit.add( Restrictions.eq("tipo", tipo));
        crit.add( Restrictions.eq("periodo", periodo));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List filtrarPorMatrizPeriodo(int matriz, int periodo){
        Criteria crit = criarSessao().createCriteria(Disciplina.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq("matriz.id", matriz));
        crit.add( Restrictions.eq("periodo", periodo));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
}
