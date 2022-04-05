package com.brenoleal.samha.persistence;

import java.util.List;

import com.brenoleal.samha.core.Coordenadoria;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class GdCoordenadoria extends GdGenerico{
    
    private GdPrincipal gdPrincipal;
    
    public GdCoordenadoria(GdPrincipal gdPrincipal){
        this.gdPrincipal = gdPrincipal;
    }
    
    public List filtrarCoordenadoriasEixo(int id) {
        Criteria crit = criarSessao().createCriteria(Coordenadoria.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq("eixo.id", id) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List buscar(String coluna, String texto) {
        Criteria crit = criarSessao().createCriteria(Coordenadoria.class);
        sessao.beginTransaction();
        crit.add( Restrictions.like(coluna, texto, MatchMode.ANYWHERE) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public Coordenadoria filtrarCoordenadoriaUnica(String colunaFiltro, int id) {
        Criteria crit = criarSessao().createCriteria(Coordenadoria.class);
        sessao.beginTransaction();
        colunaFiltro = colunaFiltro.toLowerCase();
        crit.add( Restrictions.eq(colunaFiltro, id));
        crit.setMaxResults(1);
        Coordenadoria coordenadoria = (Coordenadoria) crit.uniqueResult();
        sessao.getTransaction().commit();
        sessao.close();
        return coordenadoria;
    }
}
