package com.brenoleal.persistence.legacy;

import java.util.List;

import com.brenoleal.core.RestricaoProfessor;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class GdRestricao extends GdGenerico{
    
    private GdPrincipal gdPrincipal;
    
    public GdRestricao(GdPrincipal gdPrincipal){
        this.gdPrincipal = gdPrincipal;
    }
    
    public List filtrarPorProfessor(int id) {
        Criteria crit = criarSessao().createCriteria(RestricaoProfessor.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq("professor.id", id) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List identificarConflitoRestricao(int idProfessor, int dia, String turno) {
        Criteria crit = criarSessao().createCriteria(RestricaoProfessor.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq("professor.id", idProfessor) );
        crit.add( Restrictions.eq("dia", dia) );
        crit.add( Restrictions.eq("turno", turno) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public void excluirTodasRestricoes(){
        
        List<RestricaoProfessor> listaRestricoes = consultar(RestricaoProfessor.class);
        
        Criteria crit = criarSessao().createCriteria(RestricaoProfessor.class);
        sessao.beginTransaction();
        
        listaRestricoes.forEach((restricao) -> {
            sessao.delete(restricao);
        });
        
        sessao.getTransaction().commit();
        sessao.close();
    }
}
