package com.brenoleal.samha.persistence.legacy;

import java.util.List;

import com.brenoleal.samha.core.Professor;
import com.brenoleal.samha.core.RestricaoProfessor;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class GdProfessor extends GdGenerico{
    
    private GdPrincipal gdPrincipal;
    
    public GdProfessor(GdPrincipal gdPrincipal){
        this.gdPrincipal = gdPrincipal;
    }
    
    public List consultarAtivos() {
        Criteria crit = criarSessao().createCriteria(Professor.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq("ativo", true) );
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List buscar(String coluna, String texto) {
        Criteria crit = criarSessao().createCriteria(Professor.class);
        sessao.beginTransaction();
        crit.add( Restrictions.like(coluna, texto, MatchMode.ANYWHERE) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List filtrarPorCoordenadoria(int id) {
        Criteria crit = criarSessao().createCriteria(Professor.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq("coordenadoria.id", id) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List filtrarAtivosPorCoordenadoria(int id) {
        Criteria crit = criarSessao().createCriteria(Professor.class);
        sessao.beginTransaction();
        crit.add( Restrictions.eq("ativo", true) );
        crit.add( Restrictions.eq("coordenadoria.id", id) );
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List filtrarPorEixo(int id) {
        Criteria crit = criarSessao().createCriteria(Professor.class);
        sessao.beginTransaction();
        crit.createAlias("coordenadoria", "c");
        crit.createAlias("c.eixo", "e");
        crit.add( Restrictions.eq("e.id", id));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public List filtrarAtivosPorEixo(int id) {
        Criteria crit = criarSessao().createCriteria(Professor.class);
        sessao.beginTransaction();
        crit.createAlias("coordenadoria", "c");
        crit.createAlias("c.eixo", "e");
        crit.add( Restrictions.eq("ativo", true) );
        crit.add( Restrictions.eq("e.id", id));
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List lista = crit.list();
        sessao.getTransaction().commit();
        sessao.close();
        return lista;
    }
    
    public void excluirProfessor(Professor professor){
        
        List listaRestricoes = gdPrincipal.getGdRestricao().filtrarPorProfessor(professor.getId());
        
        try {
            sessao = criarSessao();
            sessao.beginTransaction();
            
            RestricaoProfessor restricao;
            
            for (int i = 0; i < listaRestricoes.size(); i++) {
                restricao = (RestricaoProfessor) listaRestricoes.get(i);
                sessao.delete(restricao);
            }
            
            sessao.delete(professor);
            
            sessao.getTransaction().commit();
            sessao.close();

        } catch (HibernateException e) {
            sessao.getTransaction().rollback();
            sessao.close();
            throw e;
        } 
        
    }
}
