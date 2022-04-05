package com.brenoleal.samha.persistence;


import java.util.List;

import com.brenoleal.samha.core.Oferta;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class GdOferta extends GdGenerico{
    
    private GdPrincipal gdPrincipal;
    
    public GdOferta(GdPrincipal gdPrincipal){
        this.gdPrincipal = gdPrincipal;
    }
    
    public Oferta filtrarUltimaOfertaTurma(int idTurma) {
        Criteria crit = criarSessao().createCriteria(Oferta.class);
        crit.add( Restrictions.eq("turma.id", idTurma));
        crit.addOrder(Order.desc("ano"));
        crit.addOrder(Order.desc("semestre"));
        crit.setMaxResults(1);
        Oferta oferta = (Oferta) crit.uniqueResult();
        sessao.close();
        return oferta;
    }
    
    public Oferta filtrarOfertaAnoSemestreTurma(int ano, int semestre, int idTurma) {
        Criteria crit = criarSessao().createCriteria(Oferta.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq("ano", ano));
        crit.add( Restrictions.eq("semestre", semestre));
        crit.add( Restrictions.eq("turma.id", idTurma));
        crit.setMaxResults(1);
        Oferta oferta = (Oferta) crit.uniqueResult();
        sessao.getTransaction().commit();
        sessao.close();
        return oferta;
    }
    
    public List filtrarOfertasTurma(int idTurma) {
        Criteria crit = criarSessao().createCriteria(Oferta.class);
        sessao.beginTransaction();
        crit.createAlias("turma", "t");
        crit.add( Restrictions.eq("t.id", idTurma));
        crit.addOrder(Order.desc("ano"));
        crit.addOrder(Order.desc("semestre"));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
}
